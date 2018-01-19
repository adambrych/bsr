package bsr.endpoints;

import bsr.exception.BankException;
import bsr.exception.ServiceFault;
import bsr.model.Bank;
import bsr.model.User;
import bsr.services.TransferService;
import bsr.services.UserService;
import https.www_bank_com.transfer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Class implements endpoints for transfer
 */
@Endpoint
public class TransferEndpoint
{
    private static final String NAMESPACE_URI = "https://www.bank.com/transfer";

    @Autowired
    TransferService transferService;
    @Autowired
    UserService userService;

    @Autowired
    public TransferEndpoint() {
    }

    /**
     * Method for make internal transfer
     * @param transfer
     * @return
     * @throws BankException
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "InternalTransfer")
    @ResponsePayload
    public TransferResponse internalTransfer(@RequestPayload InternalTransfer transfer) throws BankException {
        User user = userService.getUser(transfer.getToken());
        if(transfer.getAccountFrom().equals(transfer.getAccountTo()))
            throw new BankException("ERROR", new ServiceFault("Bad request", "Accounts can't be the same"));
        Bank.checkControlSum(transfer.getAccountFrom());
        Bank.checkControlSum(transfer.getAccountTo());
        transferService.saveInternalTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), transfer.getTitle(), user);
        TransferResponse response = new TransferResponse();
        response.setMessage("done");
        return response;
    }

    /**
     * Method for make transfer to account in another bank
     * @param transfer
     * @return
     * @throws BankException
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ExternalTransfer")
    @ResponsePayload
    public TransferResponse externalTransfer(@RequestPayload ExternalTransfer transfer) throws BankException {
        User user = userService.getUser(transfer.getToken());
        Bank.checkControlSum(transfer.getAccountFrom());
        Bank.checkControlSum(transfer.getAccountTo());
        if(transfer.getAccountFrom().equals(transfer.getAccountTo()))
            throw new BankException("ERROR", new ServiceFault("Bad request", "Accounts can't be the same"));
        transferService.saveExternalTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), transfer.getName(), transfer.getTitle(), user);
        TransferResponse response = new TransferResponse();
        response.setMessage("done");
        return response;
    }

    /**
     * Method for make a payment on account
     * @param transfer
     * @return
     * @throws BankException
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Payment")
    @ResponsePayload
    public TransferResponse payment(@RequestPayload Payment transfer) throws BankException {
        User user = userService.getUser(transfer.getToken());
        Bank.checkControlSum(transfer.getAccountFrom());
        transferService.savePayment(transfer.getAccountFrom(), transfer.getAmount(), user);
        TransferResponse response = new TransferResponse();
        response.setMessage("done");
        return response;
    }

    /**
     * Method to make witdrawal moey from account
     * @param transfer
     * @return
     * @throws BankException
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Withdrawal")
    @ResponsePayload
    public TransferResponse withdrawal(@RequestPayload Withdrawal transfer) throws BankException {
        User user = userService.getUser(transfer.getToken());
        Bank.checkControlSum(transfer.getAccountFrom());
        transferService.saveWithdrawal(transfer.getAccountFrom(), transfer.getAmount(), user);
        TransferResponse response = new TransferResponse();
        response.setMessage("done");
        return response;
    }
}
