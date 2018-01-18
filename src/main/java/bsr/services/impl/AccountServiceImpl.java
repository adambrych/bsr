package bsr.services.impl;

import bsr.dao.AccountDao;
import bsr.exception.AccountException;
import bsr.exception.ServiceFault;
import bsr.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bsr.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public Account getAccount(String accountNumber) throws AccountException {
        Account account = accountDao.findAccountByAccountNumber(accountNumber);
        if(account  == null)
            throw new AccountException("Account doesn't exists", new ServiceFault("404", "Account doesn't exists"));
        else
            return account;
    }

    @Override
    public void saveAccount(Account account) {
        accountDao.save(account);
    }
}
