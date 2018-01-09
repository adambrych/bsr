package bsr.services.impl;

import bsr.dao.TransferDao;
import bsr.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bsr.services.TransferService;

@Service
public class TransferServiceImpl implements TransferService{
    @Autowired
    TransferDao transferDao;

    @Override
    public void save(Transfer transfer) {
        transferDao.save(transfer);
    }
}
