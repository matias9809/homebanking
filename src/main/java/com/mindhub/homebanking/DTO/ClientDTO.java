package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Client;

import java.util.Set;
import static java.util.stream.Collectors.toSet;

public class ClientDTO {
    private long id;
    private String firstname,lastname,email;
    private Set<AccountDTO> ACCO;
    private Set<ClientLoanDTO> loan;
    private Set<CardDTO> cards;
    public ClientDTO(Client client) {

        this.id = client.getId();

        this.firstname = client.getFirstname();

        this.lastname = client.getLastname();

        this.email = client.getEmail();

        this.ACCO=client.getAccount().stream().map(AccountDTO::new).collect(toSet());

        this.loan=client.getClientLoans().stream().map(ClientLoanDTO::new).collect(toSet());

        this.cards=client.getCards().stream().map(CardDTO::new).collect(toSet());
    }
    public String getFirstname(){
        return firstname;
    }
    public String getLastname(){
        return lastname;
    }
    public String getEmail(){
        return email;
    }

    public Set<AccountDTO> getAccount() {
        return ACCO;
    }

    public long getId(){
        return id;
    }

    public Set<ClientLoanDTO> getLoan() {
        return loan;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
