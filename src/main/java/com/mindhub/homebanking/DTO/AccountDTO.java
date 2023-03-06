package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private Double balance;
    private Set<TransactionDTO> transaction;
    public AccountDTO(Account acco){
        this.id= acco.getId();
        this.number= acco.getNumber();
        this.creationDate=acco.getCreationDate();
        this.balance=acco.getBalance();
        this.transaction=acco.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
    }
    public String getNumber(){
        return number;
    }

    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    public double getBalance(){
        return balance;
    }


    public long getId(){
        return id;
    }
    public Set<TransactionDTO> getTransaction() {
        return transaction;
    }
}
