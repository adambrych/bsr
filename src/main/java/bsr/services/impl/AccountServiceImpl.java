package bsr.services.impl;

import bsr.dao.AccountDao;
import bsr.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bsr.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public Account getAccount(String accountNumber) {
        return accountDao.findAccountByAccountNumber(accountNumber);
    }

    @Override
    public void saveAccount(Account account) {
        accountDao.save(account);
    }
}
