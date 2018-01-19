package bsr.services;

import bsr.exception.BankException;
import bsr.model.Transfer;
import bsr.model.User;

public interface TransferService {
    void save(Transfer transfer);
    void saveInternalTransfer(String from, String to, long amount, String title, User user) throws BankException;
    void savePayment(String from, long amount, User user) throws BankException;
    void saveWithdrawal(String from, long amount, User user) throws BankException;
    void saveExternalTransfer(String from, String to, long amount, String name, String title, User user) throws BankException;
}
