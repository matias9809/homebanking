package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Loan;


import java.util.List;

public class LoanDTO {
    private Long id;
    private String name;
    private Integer maxAmount;

    private List<Integer> payment;
    private Double fees;

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payment = loan.getPayment();
        this.fees= loan.getFees();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayment() {
        return payment;
    }

    public Double getFees() {return fees;}
}
