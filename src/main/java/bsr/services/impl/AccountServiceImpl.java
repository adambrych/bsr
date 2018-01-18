package bsr.services.impl;

import bsr.dao.AccountDao;
import bsr.dao.TransferDao;
import bsr.exception.AccountException;
import bsr.exception.ServiceFault;
import bsr.model.Account;
import bsr.model.Transfer;
import bsr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bsr.services.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TransferDao transferDao;

    @Override
    public Account getAccount(String accountNumber) throws AccountException {
        Account account = accountDao.findAccountByAccountNumber(accountNumber);
        if(account  == null)
            throw new AccountException("Account doesn't exists", new ServiceFault("404", "Account doesn't exists"));
        else
            return account;
    }

    @Override
    public List<Transfer> getAccountHistory(String accountNumber) throws AccountException {
        Account account = getAccount(accountNumber);
        return transferDao.findTransferByAccountFrom(account.getAccountNumber());
    }

    @Override
    public void saveAccount(Account account) {
        accountDao.save(account);
    }

    @Override
    public void createAccount(User user) {

    }
}
