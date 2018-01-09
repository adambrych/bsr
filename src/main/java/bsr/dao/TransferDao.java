package bsr.dao;

import bsr.model.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferDao extends CrudRepository<Transfer, Long> {
}
