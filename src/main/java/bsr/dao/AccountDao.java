package bsr.dao;

import bsr.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for account
 */
@Repository
public interface AccountDao extends CrudRepository<Account, Long> {
    /**
     * Get account using number and user id
     * @param accountNumber
     * @param credentials
     * @return
     */
    Account findAccountByAccountNumberAndCredentials(String accountNumber, int credentials);

    /**
     * Get account using account number
     * @param accountNumber
     * @return
     */
    Account findAccountByAccountNumber(String accountNumber);
}
