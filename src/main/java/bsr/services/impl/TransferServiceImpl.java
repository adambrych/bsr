package bsr.services.impl;

import bsr.dao.AccountDao;
import bsr.dao.TransferDao;
import bsr.exception.BankException;
import bsr.exception.ServiceFault;
import bsr.model.Account;
import bsr.model.Transfer;
import bsr.model.TransferType;
import bsr.model.User;
import bsr.rest.ResponsePayload;
import bsr.services.AccountService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import bsr.services.TransferService;
import org.springframework.web.client.RestTemplate;

import java.io.*;


@Service
public class TransferServiceImpl implements TransferService{

    private static final String ERROR = "ERROR2";
    private static final String MESSAGE = "404 Not Found";
    private static final String DESCRIPTION = "Account doesn't exist";
    private static final String MESSAGE_AMOUNT = "Bad Request";
    private static final String DESCRIPTION_LOWER_THAN_0 = "Amount can't be lower than 0";
    private static final String DESCRIPTION_AMOUNT_BALANCE = "Amount can't be greater than account balance";
    private static final String FILENAME = "Banki.csv";

    @Autowired
    TransferDao transferDao;
    @Autowired
    AccountDao accountDao;

    @Override
    public void save(Transfer transfer) {
        transferDao.save(transfer);
    }

    @Override
    public void saveInternalTransfer(String from, String to, long amount, User user) throws BankException {

        Account accountFrom = getAccount(from, user);
        Account accountTo = getAccount(to);
        checkTransfer(accountFrom, amount);
        changeBalance(accountFrom, accountTo, amount);
        accountDao.save(accountFrom);
        accountDao.save(accountTo);
        Transfer transfer = new Transfer(accountFrom.getAccountNumber(), accountTo.getAccountNumber(), amount, accountFrom.getBalance(), TransferType.INTERNAL_TRANSFER);
        transferDao.save(transfer);
    }

    @Override
    public void savePayment(String from, long amount, User user) throws BankException {
        Account accountFrom = getAccount(from, user);
        checkTransfer(accountFrom, amount);
        changeBalanceByPayment(accountFrom, amount);
        accountDao.save(accountFrom);
        Transfer transfer = new Transfer(accountFrom.getAccountNumber(), "", amount, accountFrom.getBalance(), TransferType.PAYMENT);
        transferDao.save(transfer);
    }

    @Override
    public void saveWithdrawal(String from, long amount, User user) throws BankException {
        Account accountFrom = getAccount(from, user);
        checkTransfer(accountFrom, amount);
        changeBalanceByWithdrawal(accountFrom, amount);
        accountDao.save(accountFrom);
        Transfer transfer = new Transfer(accountFrom.getAccountNumber(), "", amount, accountFrom.getBalance(), TransferType.WITHDRAWAL);
        transferDao.save(transfer);
    }


    @Override
    public void saveExternalTransfer(String from, String to, long amount, String name, String title, User user) throws BankException {
        Account accountFrom = getAccount(from, user);
        checkTransfer(accountFrom, amount);
        String url = readCsv(to);
        if(url.equals(""))
            throw new BankException("ERROR", new ServiceFault("Bad Request", "Can't find external bank using destination account"));
        final String uri = url + "/accounts/" + to + "/history";
        JSONObject request = new JSONObject()
                .put("source_account", from)
                .put("amount", amount)
                .put("title", title)
                .put("name", name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.basicAuthorization("admin", "admin").build();
        ResponseEntity<ResponsePayload> response = restTemplate.exchange(uri, HttpMethod.POST,  new HttpEntity<String>(request.toString(), headers),  ResponsePayload.class);
        if(response.getStatusCode() == HttpStatus.CREATED){
            accountFrom.setBalance(accountFrom.getBalance() - amount);
            accountDao.save(accountFrom);
            Transfer transfer = new Transfer();
            transfer.setType(TransferType.EXTERNAL_TRANSFER);
            transfer.setAccountTo(to);
            transfer.setAccountFrom(from);
            transfer.setAmount(amount);
            transfer.setBalance(accountFrom.getBalance());
            transferDao.save(transfer);
        }
        //ResponsePayload result = restTemplate.getForObject(uri, ResponsePayload.class, request, headers);

    }

    private Account getAccount(String number, User user) throws BankException {
        Account accountFrom = accountDao.findAccountByAccountNumberAndCredentials(number, user.getId());
        if(accountFrom == null)
            throw new BankException(ERROR, new ServiceFault(MESSAGE, DESCRIPTION));
        return accountFrom;
    }

    private Account getAccount(String number) throws BankException {
        Account accountFrom = accountDao.findAccountByAccountNumber(number);
        if(accountFrom == null)
            throw new BankException(ERROR, new ServiceFault(MESSAGE, DESCRIPTION));
        return accountFrom;
    }

    private void changeBalance(Account from, Account to, long amount){
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
    }

    private void changeBalanceByPayment(Account from, long amount){
        from.setBalance(from.getBalance() + amount);
    }
    private void changeBalanceByWithdrawal(Account from, long amount){
        from.setBalance(from.getBalance() - amount);
    }

    private void checkTransfer(Account from, long amount) throws BankException {
        if(amount <= 0)
            throw new BankException(ERROR, new ServiceFault(MESSAGE_AMOUNT, DESCRIPTION_LOWER_THAN_0));
        if(from.getBalance() < amount)
            throw new BankException(ERROR, new ServiceFault(MESSAGE_AMOUNT, DESCRIPTION_AMOUNT_BALANCE));
    }

    private String readCsv(String accountTo){
        String findUrl = "";
        try {
            Reader in = new BufferedReader(new InputStreamReader(
                    this.getClass().getResourceAsStream("/" + FILENAME)));
            Iterable<CSVRecord> records = null;
            records = CSVFormat.RFC4180.withHeader("ID Banku", "URL", "przykładowe konto").parse(in);

            for (CSVRecord record : records) {

                String id = record.get("ID Banku");
                String url = record.get("URL");
                String account = record.get("przykładowe konto");
                if(id.equals(accountTo.substring(2, 10)))
                    findUrl = url;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return findUrl;
    }
}
