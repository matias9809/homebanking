package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private Long id;
    private Double amount;
    private int payments;
    private Long Loans_id;
    private String name;

    public ClientLoanDTO(ClientLoan clientLoans){
        this.id=clientLoans.getId();
        this.amount= clientLoans.getAmount();
        this.payments=clientLoans.getPayment();
        this.Loans_id=clientLoans.getLoan().getId();
        this.name=clientLoans.getLoan().getName();
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public Long getLoans_id() {
        return Loans_id;
    }

    public String getName() {
        return name;
    }
}
