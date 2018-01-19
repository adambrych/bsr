package bsr.services;

import bsr.exception.AccountException;
import bsr.model.Transfer;
import bsr.model.User;

public interface TransferService {
    void save(Transfer transfer);
    void saveInternalTransfer(String from, String to, long amount, User user) throws AccountException;
    void savePayment(String from, long amount, User user) throws AccountException;
    void saveWithdrawal(String from, long amount, User user) throws AccountException;
    void saveExternalTransfer(String from, String to, long amount, String name, String title, User user) throws AccountException;
}
