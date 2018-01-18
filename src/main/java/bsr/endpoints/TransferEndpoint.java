package bsr.endpoints;

import bsr.exception.AccountException;
import bsr.services.TransferService;
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
    public TransferEndpoint() {
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "InternalTransfer")
    @ResponsePayload
    public TransferResponse internalTransfer(@RequestPayload InternalTransfer transfer) throws AccountException {
        transferService.saveInternalTransfer(transfer.getAccountFrom(), transfer.getAccountFrom(), transfer.getAmount());
        TransferResponse response = new TransferResponse();
        response.setMessage("done");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ExternalTransfer")
    @ResponsePayload
    public TransferResponse externalTransfer(@RequestPayload ExternalTransfer transfer) throws AccountException {
        transferService.saveExternalTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), transfer.getName(), transfer.getTitle());
        TransferResponse response = new TransferResponse();
        response.setMessage("done");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Payment")
    @ResponsePayload
    public TransferResponse payment(@RequestPayload Payment transfer) throws AccountException {
        transferService.savePayment(transfer.getAccountFrom(), transfer.getAmount());
        TransferResponse response = new TransferResponse();
        response.setMessage("done");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Withdrawal")
    @ResponsePayload
    public TransferResponse withdrawal(@RequestPayload Withdrawal transfer) throws AccountException {
        transferService.saveWithdrawal(transfer.getAccountFrom(), transfer.getAmount());
        TransferResponse response = new TransferResponse();
        response.setMessage("done");
        return response;
    }
}
