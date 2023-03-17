package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private Double balance;
    private TypeAccount typeAccount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    private State state;
    @OneToMany(mappedBy="account", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    Set<Transaction> transactions = new HashSet<>();
    public Account(){
    }
    public Account(String number,LocalDateTime Date,Double balance,TypeAccount typeAccount){
        this.number=number;
        this.creationDate=Date;
        this.balance=balance;
        this.state=State.ACTIVE;
        this.typeAccount=typeAccount;
    }
    public String getNumber(){
        return number;
    }
    public void setNumber(String number){
        this.number=number;
    }
    public LocalDateTime getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(LocalDateTime date){
        this.creationDate=date;
    }
    public double getBalance(){
        return balance;
    }
    public void setBalance(Double balance){
        this.balance=balance;
    }
    public long getId(){
        return id;
    }
    public void setClient(Client client){
        this.client =client;
    }

    public Client getClient(){
        return client;
    }
    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public TypeAccount getTypeAccount() {
        return typeAccount;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }

}
