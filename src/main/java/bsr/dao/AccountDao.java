package bsr.dao;

import bsr.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends CrudRepository<Account, Long> {
    Account findAccountByAccountNumber(String accountNumber);
}
