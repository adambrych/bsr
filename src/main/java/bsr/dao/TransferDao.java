package bsr.dao;

import bsr.model.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for transfers table
 */
@Repository
public interface TransferDao extends CrudRepository<Transfer, Long> {
    /**
     * find transfer by account number. Get historu
     * @param accountFrom
     * @return
     */
    List<Transfer> findTransferByAccountFrom(String accountFrom);
    /**
     * find transfer by account number. Get historu
     * @param accountTo
     * @return
     */
    List<Transfer> findTransferByAccountTo(String accountTo);
}
