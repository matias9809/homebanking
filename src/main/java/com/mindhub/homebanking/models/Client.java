package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String Password;
    @OneToMany(mappedBy="client",fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();
    @OneToMany(mappedBy="client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();
    @OneToMany(mappedBy="client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    public Client(){}

    public Client(String lastname, String firstname,String email,String Password){
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.Password=Password;
    }
    public String getFirstname(){
        return firstname;
    }
    public void setFirstname(String firstname){this.firstname=firstname;}
    public String getLastname(){
        return lastname;
    }
    public void setLastname(String lastname){
        this.lastname=lastname;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }

    public long getId(){
        return id;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public Set<Account> getAccount() {
        return accounts;
    }

    public void setAccount(Set<Account> account){
        this.accounts=account;
    }
    public Set<Card> getCards() {
        return cards;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
    public void addAccounts(Account account) {
        account.setClient(this);
        accounts.add(account);
    }

    @JsonIgnore
    public List<Loan> getLoan() {
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(toList());
    }

    public void addClientLoan (ClientLoan clientLoans1) {
        clientLoans1.setClient(this);
        clientLoans.add(clientLoans1);
    }

    public void addCard (Card card) {
        card.setClient(this);
        cards.add(card);
    }
}
