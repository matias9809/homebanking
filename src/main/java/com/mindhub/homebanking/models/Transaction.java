package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;
    public Transaction(){
    }
    public Transaction(TransactionType type, Double amount, String description, LocalDateTime date){
        this.amount=amount;
        this.type=type;
        this.description=description;
        this.date=date;
    }
    public TransactionType getType(){
        return this.type;
    }
    public Double getAmount(){
        return this.amount;
    }
    public String getDescription(){
        return this.description;
    }
    public LocalDateTime getDate(){
        return this.date;
    }
    public Long getId(){
        return this.id;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setAccount(Account account) {
        this.account =account;
    }
    public Account getAccount(){
        return account;
    }
}
