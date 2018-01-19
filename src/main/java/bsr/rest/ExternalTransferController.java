package bsr.rest;

import bsr.exception.AccountException;
import bsr.model.Account;
import bsr.model.Bank;
import bsr.model.Transfer;
import bsr.model.TransferType;
import bsr.services.AccountService;
import bsr.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExternalTransferController {

    @Autowired
    AccountService accountService;
    @Autowired
    TransferService transferService;

    @RequestMapping(value = "/accounts/{accountNumber}/history", method = RequestMethod.POST)
    public ResponseEntity externalTransfer(@PathVariable String accountNumber, @RequestBody RequestPayload payload) {
        try {
            Bank.checkControlSum(accountNumber);
        } catch (AccountException e) {
            e.printStackTrace();
        }
        Account to;
        try {
            to = accountService.getAccount(accountNumber);
        } catch (AccountException e) {
            ResponsePayload response = new ResponsePayload("nr_konta", "Destiny account doesn't exist");
            return new ResponseEntity<ResponsePayload>(response, HttpStatus.NOT_FOUND);
        }
        if(payload.getAmount()<=0){
            ResponsePayload response = new ResponsePayload("amount", "Amount can't be less or equal zero");
            return new ResponseEntity<ResponsePayload>(response, HttpStatus.BAD_REQUEST);
        }
        to.setBalance(to.getBalance() + payload.getAmount());
        accountService.saveAccount(to);
        Transfer transfer = new Transfer();
        transfer.setAccountFrom(payload.getSource_account());
        transfer.setAmount(payload.getAmount());
        transfer.setAccountTo(to.getAccountNumber());
        transfer.setType(TransferType.EXTERNAL_TRANSFER);
        transferService.save(transfer);
        return new ResponseEntity(HttpStatus.OK);
    }
}
