package bsr.endpoints;

import bsr.exception.AccountException;
import bsr.model.Transfer;
import bsr.services.AccountService;
import bsr.soap.account.CreateBankAccount;
import bsr.soap.account.GetAccountHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import bsr.soap.account.CreateAccount;

import java.util.List;

@Endpoint
public class AccountEndpoint {
    private static final String NAMESPACE_URI = "https://www.bank.com/account";

    @Autowired
    AccountService accountService;

    @Autowired
    public AccountEndpoint(){

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateAccount")
    @ResponsePayload
    public String createAccount(@RequestPayload CreateAccount account) throws AccountException {

        return "done";
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateBankAccount")
    @ResponsePayload
    public String createBankAccount(@RequestPayload CreateBankAccount account) throws AccountException {

        return "done";
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAccountHistory")
    @ResponsePayload
    public List<Transfer> getAccountHistory(@RequestPayload GetAccountHistory accountHistory) throws AccountException {
        return accountService.getAccountHistory(accountHistory.getAccountNumber());
    }
}
