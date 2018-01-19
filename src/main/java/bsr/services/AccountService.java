package bsr.services;

import bsr.exception.BankException;
import bsr.model.Account;
import bsr.model.Transfer;
import bsr.model.User;

import java.util.List;

/**
 * Service for account
 */
public interface AccountService {
    /**
     * Get account using account number
     * @param accountNumber
     * @return
     * @throws BankException
     */
    Account getAccount(String accountNumber) throws BankException;

    /**
     * Get account using accountnumber and check user id
     * @param accountNumber
     * @param user
     * @return
     * @throws BankException
     */
    Account getAccount(String accountNumber, User user) throws BankException;

    /**
     * get account history using account number and check userid
     * @param accountNumber
     * @param user
     * @return
     * @throws BankException
     */
    List<Transfer> getAccountHistory(String accountNumber, User user) throws BankException;

    /**
     * Save account
     * @param account
     */
    void saveAccount(Account account);
}
