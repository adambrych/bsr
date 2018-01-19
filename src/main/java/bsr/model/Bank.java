package bsr.model;


import bsr.exception.BankException;
import bsr.exception.ServiceFault;

import java.math.BigInteger;

public class Bank {
    private static final String branchNumber = "00117325";
    private static final String PL = "2521";

    public static void checkControlSum(String accountNumber) throws BankException {
        String firstTwoChars = accountNumber.substring(0, 2);
        accountNumber = accountNumber.substring(2);
        accountNumber = accountNumber + PL + firstTwoChars;
        BigInteger number = new BigInteger(accountNumber);
        BigInteger mod = number.mod(new BigInteger("97"));
        if(!mod.equals(1))
            throw new BankException("ERROR", new ServiceFault("Control sum", "Control sum is wrong"));

    }
}
