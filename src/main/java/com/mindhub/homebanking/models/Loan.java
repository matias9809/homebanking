package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private int maxAmount;
    @ElementCollection
    @Column(name="payments")
    private List<Integer> payment = new ArrayList<>();
    @OneToMany(mappedBy="loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoan = new HashSet<>();
    public Loan() {
    }
    public Loan(String name, int maxAmount, List<Integer> payment) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayment() {
        return payment;
    }
    public void setPayment(List<Integer> payment) {
        this.payment = payment;
    }

    public Set<ClientLoan> getClientLoan() {
        return clientLoan;
    }

    public void setClientLoan(Set<ClientLoan> clientLoan) {
        this.clientLoan = clientLoan;
    }
    public List<Client> getClients() {
        return clientLoan.stream().map(clientLoan -> clientLoan.getClient()).collect(toList());
    }
    public void addClientLoans (ClientLoan clientLoans1) {
        clientLoans1.setLoan(this);
        clientLoan.add(clientLoans1);
    }

}
