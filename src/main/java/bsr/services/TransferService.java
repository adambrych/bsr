package bsr.services;

import bsr.exception.AccountException;
import bsr.model.Transfer;

public interface TransferService {
    void save(Transfer transfer);
    void saveInternalTransfer(String from, String to, long amount) throws AccountException;
    void savePayment(String from, long amount) throws AccountException;
    void saveWithdrawal(String from, long amount) throws AccountException;
}
