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
    @Column
    private String accountFrom;
    @Column
    private String accountTo;
    @Column
    private long amount;
    @Column
    private long balance;
    @Column
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
        transferForResponse.setType(type.getDescription());
        return transferForResponse;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
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
