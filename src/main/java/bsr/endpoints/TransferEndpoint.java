package bsr.endpoints;

import bsr.exception.AccountException;
import bsr.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import bsr.soap.transfer.ExternalTransfer;
import bsr.soap.transfer.InternalTransfer;
import bsr.soap.transfer.Payment;
import bsr.soap.transfer.Withdrawal;

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
    public String internalTransfer(@RequestPayload InternalTransfer transfer) throws AccountException {
        transferService.saveInternalTransfer(transfer.getAccountFrom(), transfer.getAccountFrom(), transfer.getAmount());
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
        transferService.savePayment(transfer.getAccountFrom(), transfer.getAmount());
        return "done";
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Withdrawal")
    @ResponsePayload
    public String withdrawal(@RequestPayload Withdrawal transfer) throws AccountException {
        transferService.saveWithdrawal(transfer.getAccountFrom(), transfer.getAmount());
        return "done";
    }
}
