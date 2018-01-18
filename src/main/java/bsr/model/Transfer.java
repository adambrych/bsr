package bsr.model;


import https.www_bank_com.account.TransferForResponse;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String accountFrom;
    private String accountTo;
    private long amount;
    private long balance;
    private TransferType type;

    public Transfer(String from, String to, long amount,long balance, TransferType type){
        this.amount = amount;
        this.accountFrom = from;
        this.accountTo = to;
        this.balance = balance;
        this.type = type;
    }

    public TransferForResponse toResponse(){
        TransferForResponse transferForResponse = new TransferForResponse();
        transferForResponse.setBalance(balance);
        transferForResponse.setAccountFrom(accountFrom);
        transferForResponse.setAccountTo(accountTo);
        transferForResponse.setType(type.toString());
        return transferForResponse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public TransferType getType() {
        return type;
    }

    public void setType(TransferType type) {
        this.type = type;
    }
}
