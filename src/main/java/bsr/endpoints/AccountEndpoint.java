package bsr.endpoints;

import bsr.exception.AccountException;
import bsr.model.Transfer;
import bsr.model.User;
import bsr.services.AccountService;

import bsr.services.UserService;
import https.www_bank_com.account.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


import java.util.ArrayList;
import java.util.List;

@Endpoint
public class AccountEndpoint {
    private static final String NAMESPACE_URI = "https://www.bank.com/account";

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    public AccountEndpoint(){

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LoginRequest")
    @ResponsePayload
    public TokenResponse login(@RequestPayload LoginRequest account) throws AccountException {
        User user = userService.getUser(account.getLogin(), account.getPassword());
        TokenResponse response = new TokenResponse();
        response.setToken(user.getLogin());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AccountHistoryRequest")
    @ResponsePayload
    public AccountHistoryResponse getAccountHistory(@RequestPayload AccountHistoryRequest accountHistory) throws AccountException {
        User user = userService.getUser(accountHistory.getToken());
    //    Bank.checkControlSum(accountHistory.getAccountNumber());
        List<Transfer> transfers = accountService.getAccountHistory(accountHistory.getAccountNumber(), user);
        AccountHistoryResponse response = new AccountHistoryResponse();
        response.setAccountHistory(toResponse(transfers));
        return response;
    }

    private List<TransferForResponse> toResponse(List<Transfer> transfers){
        List<TransferForResponse> response = new ArrayList<TransferForResponse>();
        for(Transfer transfer : transfers){
            response.add(transfer.toResponse());
        }
        return response;
    }
}
