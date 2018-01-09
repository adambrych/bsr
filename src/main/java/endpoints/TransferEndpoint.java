package endpoints;

import exception.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.transfer.ExternalTransfer;
import soap.transfer.InternalTransfer;
import soap.transfer.Payment;
import soap.transfer.Withdrawal;

@Endpoint
public class TransferEndpoint
{
    private static final String NAMESPACE_URI = "https://www.bank.com/transfer";


    @Autowired
    public TransferEndpoint() {
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "InternalTransfer")
    @ResponsePayload
    public String internalTransfer(@RequestPayload InternalTransfer transfer) throws AccountException {
        /*Account from = DataBase.getAccount(transfer.getAccountFrom());
        Account to = DataBase.getAccount(transfer.getAccountTo());
        checkTransfer(transfer.getAmount(), from, to, transfer.getAccountFrom(), transfer.getAccountTo());
        Transfer newTransfer = new Transfer(from, to, transfer.getAmount(), TransferType.INTERNAL_TRANSFER);
        DataBase.saveTransfer(newTransfer);
        DataBase.updateAccount(from);*/
        return "done";
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ExternalTransfer")
    @ResponsePayload
    public String externalTransfer(@RequestPayload ExternalTransfer transfer) {
        //TODO
        return "done";
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Payment")
    @ResponsePayload
    public String payment(@RequestPayload Payment transfer) throws AccountException {
        /*Account from = DataBase.getAccount(transfer.getAccountFrom());
        checkTransfer(transfer.getAmount(), from, transfer.getAccountFrom(), TransferType.PAYMENT);
        Transfer newTransfer = new Transfer(from, transfer.getAmount(), TransferType.INTERNAL_TRANSFER);
        DataBase.saveTransfer(newTransfer);
        DataBase.updateAccount(from);*/
        return "done";
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Withdrawal")
    @ResponsePayload
    public String withdrawal(@RequestPayload Withdrawal transfer) throws AccountException {
        /*Account from = DataBase.getAccount(transfer.getAccountFrom());
        checkTransfer(transfer.getAmount(), from, transfer.getAccountFrom(), TransferType.WITHDRAWAL);
        Transfer newTransfer = new Transfer(from, transfer.getAmount(), TransferType.INTERNAL_TRANSFER);
        DataBase.saveTransfer(newTransfer);
        DataBase.updateAccount(from);*/
        return "done";
    }

    /*private void checkTransfer(long amount, Account from, Account to, String fromNumber, String toNumber) throws AccountException {
        checkAccount(from, fromNumber);
        checkAccount(to, toNumber);
        checkAmount(amount);
        checkBalance(from, amount);
    }

    private void checkTransfer(long amount, Account from, String fromNumber, TransferType type) throws AccountException {
        checkAccount(from, fromNumber);
        checkAmount(amount);
        if(type == TransferType.WITHDRAWAL)
            checkBalance(from, amount);
    }

    private void checkAccount(Account account, String accountNumber) throws AccountException {
        if(account == null)
            throw new AccountException("ERROR", new ServiceFault("NOT FOUND", "Account with number " + accountNumber + " not found."));
    }

    private void checkAmount(long amount) throws AccountException {
        if(Transfer.isAmountMinus(amount))
            throw new AccountException("ERROR", new ServiceFault("NOT FOUND", "Amount can't be minus"));
    }
    private void checkBalance(Account account, long amount) throws AccountException {
        if(Transfer.compareAmountWithBalance(account.getBalance(), amount))
            throw new AccountException("ERROR", new ServiceFault("NOT FOUND", "Amount is bigger than account balance: " + account.getBalance()));
    }*/
}
