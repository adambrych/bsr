package bsr.services;

import bsr.model.Account;

public interface AccountService {
    Account getAccount(String accountNumber);
    void saveAccount(Account account);
}
