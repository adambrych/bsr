package bsr.endpoints;

import bsr.exception.AccountException;
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "InternalTransfer")
    @ResponsePayload
    public TransferResponse internalTransfer(@RequestPayload InternalTransfer transfer) throws AccountException {
        User user = userService.getUser(transfer.getToken());
        Bank.checkControlSum(transfer.getAccountFrom());
        Bank.checkControlSum(transfer.getAccountTo());
        transferService.saveInternalTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(),user);
        TransferResponse response = new TransferResponse();
        response.setMessage("done");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ExternalTransfer")
    @ResponsePayload
    public TransferResponse externalTransfer(@RequestPayload ExternalTransfer transfer) throws AccountException {
        User user = userService.getUser(transfer.getToken());
        Bank.checkControlSum(transfer.getAccountFrom());
        Bank.checkControlSum(transfer.getAccountTo());
        transferService.saveExternalTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), transfer.getName(), transfer.getTitle(), user);
        TransferResponse response = new TransferResponse();
        response.setMessage("done");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Payment")
    @ResponsePayload
    public TransferResponse payment(@RequestPayload Payment transfer) throws AccountException {
        User user = userService.getUser(transfer.getToken());
        Bank.checkControlSum(transfer.getAccountFrom());
        transferService.savePayment(transfer.getAccountFrom(), transfer.getAmount(), user);
        TransferResponse response = new TransferResponse();
        response.setMessage("done");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Withdrawal")
    @ResponsePayload
    public TransferResponse withdrawal(@RequestPayload Withdrawal transfer) throws AccountException {
        User user = userService.getUser(transfer.getToken());
        Bank.checkControlSum(transfer.getAccountFrom());
        transferService.saveWithdrawal(transfer.getAccountFrom(), transfer.getAmount(), user);
        TransferResponse response = new TransferResponse();
        response.setMessage("done");
        return response;
    }
}
