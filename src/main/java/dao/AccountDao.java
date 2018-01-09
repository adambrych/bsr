package dao;

import model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountDao extends CrudRepository<Account, Long> {
    Account findAccountByAccountNumber(String accountNumber);
}
