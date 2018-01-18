package bsr.services;

import bsr.exception.AccountException;
import bsr.model.Account;
import bsr.model.Transfer;
import bsr.model.User;

import java.util.List;

public interface AccountService {
    Account getAccount(String accountNumber) throws AccountException;
    List<Transfer> getAccountHistory(String accountNumber) throws AccountException;
    void saveAccount(Account account);
    void createAccount(User user);
}
