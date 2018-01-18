package bsr.services.impl;

import bsr.dao.AccountDao;
import bsr.dao.TransferDao;
import bsr.exception.AccountException;
import bsr.exception.ServiceFault;
import bsr.model.Account;
import bsr.model.Transfer;
import bsr.model.TransferType;
import bsr.rest.RequestPayload;
import bsr.rest.ResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bsr.services.TransferService;
import org.springframework.web.client.RestTemplate;

@Service
public class TransferServiceImpl implements TransferService{

    private static final String ERROR = "ERROR2";
    private static final String MESSAGE = "404 Not Found";
    private static final String DESCRIPTION = "Account doesn't exist";
    private static final String MESSAGE_AMOUNT = "Bad Request";
    private static final String DESCRIPTION_LOWER_THAN_0 = "Amount can't be lower than 0";
    private static final String DESCRIPTION_AMOUNT_BALANCE = "Amount can't be greater than account balance";

    @Autowired
    TransferDao transferDao;
    @Autowired
    AccountDao accountDao;

    @Override
    public void save(Transfer transfer) {
        transferDao.save(transfer);
    }

    @Override
    public void saveInternalTransfer(String from, String to, long amount) throws AccountException {
        Account accountFrom = getAccount(from);
        Account accountTo = getAccount(to);
        checkTransfer(accountFrom, amount);
        changeBalance(accountFrom, accountTo, amount);
        accountDao.save(accountFrom);
        accountDao.save(accountTo);
        Transfer transfer = new Transfer(accountFrom.getAccountNumber(), accountTo.getAccountNumber(), amount, accountFrom.getBalance(), TransferType.INTERNAL_TRANSFER);
        transferDao.save(transfer);
    }

    @Override
    public void savePayment(String from, long amount) throws AccountException {
        Account accountFrom = getAccount(from);
        checkTransfer(accountFrom, amount);
        changeBalance(accountFrom, amount);
        accountDao.save(accountFrom);
        Transfer transfer = new Transfer(accountFrom.getAccountNumber(), "", amount, accountFrom.getBalance(), TransferType.PAYMENT);
        transferDao.save(transfer);
    }

    @Override
    public void saveWithdrawal(String from, long amount) throws AccountException {
        Account accountFrom = getAccount(from);
        checkTransfer(accountFrom, amount);
        changeBalanceByPayment(accountFrom, amount);
        accountDao.save(accountFrom);
        Transfer transfer = new Transfer(accountFrom.getAccountNumber(), "", amount, accountFrom.getBalance(), TransferType.PAYMENT);
        transferDao.save(transfer);
    }

    @Override
    public void saveExternalTransfer(String from, String to, long amount, String name, String title) throws AccountException {
        Account accountFrom = getAccount(from);
        final String uri = "http://localhost:8080/accounts/" + to + "/history";
        RequestPayload request = new RequestPayload();
        request.setAmount(amount);
        request.setSource_account(from);
        request.setName(name);
        request.setTitle(title);
        RestTemplate restTemplate = new RestTemplate();
        ResponsePayload result = restTemplate.getForObject(uri, ResponsePayload.class, request);
        System.out.println(result);
    }

    private Account getAccount(String number) throws AccountException {
        Account accountFrom = accountDao.findAccountByAccountNumber(number);
        if(accountFrom == null)
            throw new AccountException(ERROR, new ServiceFault(MESSAGE, DESCRIPTION));
        return accountFrom;
    }

    private void changeBalance(Account from, Account to, long amount){
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
    }

    private void changeBalance(Account from, long amount){
        from.setBalance(from.getBalance() - amount);
    }

    private void changeBalanceByPayment(Account from, long amount){
        from.setBalance(from.getBalance() + amount);
    }

    private void checkTransfer(Account from, long amount) throws AccountException {
        if(amount < 0)
            throw new AccountException(ERROR, new ServiceFault(MESSAGE_AMOUNT, DESCRIPTION_LOWER_THAN_0));
        if(from.getBalance() < amount)
            throw new AccountException(ERROR, new ServiceFault(MESSAGE_AMOUNT, DESCRIPTION_AMOUNT_BALANCE));
    }
}
