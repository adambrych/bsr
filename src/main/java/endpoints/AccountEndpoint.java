package endpoints;

import exception.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.account.CreateAccount;

@Endpoint
public class AccountEndpoint {
    private static final String NAMESPACE_URI = "https://www.bank.com/account";

    @Autowired
    public AccountEndpoint(){

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateAccount")
    @ResponsePayload
    public String createAccount(@RequestPayload CreateAccount account) throws AccountException {

        return "done";
    }
}
