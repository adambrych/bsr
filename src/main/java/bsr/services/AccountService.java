package bsr.services;

import bsr.exception.AccountException;
import bsr.model.Account;

public interface AccountService {
    Account getAccount(String accountNumber) throws AccountException;
    void saveAccount(Account account);
}
