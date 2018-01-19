package bsr.endpoints;

import bsr.exception.BankException;
import bsr.model.Bank;
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

/**
 * Class implements account endpoint
 */
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

    /**
     * Authentication
     * @param account
     * @returntoken for authentication
     * @throws BankException
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LoginRequest")
    @ResponsePayload
    public TokenResponse login(@RequestPayload LoginRequest account) throws BankException {
        User user = userService.getUser(account.getLogin(), account.getPassword());
        TokenResponse response = new TokenResponse();
        response.setToken(user.getLogin());

        return response;
    }

    /**
     * Get history of transactions on account
     * @param accountHistory
     * @return
     * @throws BankException
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AccountHistoryRequest")
    @ResponsePayload
    public AccountHistoryResponse getAccountHistory(@RequestPayload AccountHistoryRequest accountHistory) throws BankException {
        User user = userService.getUser(accountHistory.getToken());
        Bank.checkControlSum(accountHistory.getAccountNumber());
        List<Transfer> transfers = accountService.getAccountHistory(accountHistory.getAccountNumber(), user);
        AccountHistoryResponse response = new AccountHistoryResponse();
        response.setAccountHistory(toResponse(transfers));
        return response;
    }

    /**
     * CHange list transfer to response
     * @param transfers
     * @return
     */
    private List<TransferForResponse> toResponse(List<Transfer> transfers){
        List<TransferForResponse> response = new ArrayList<TransferForResponse>();
        for(Transfer transfer : transfers){
            response.add(transfer.toResponse());
        }
        return response;
    }
}
