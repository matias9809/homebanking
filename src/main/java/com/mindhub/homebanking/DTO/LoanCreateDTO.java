package com.mindhub.homebanking.DTO;

import java.util.List;

public class LoanCreateDTO {
    private String name;
    private Integer maxAmount;

    private List<Integer> payment;
    private Double fees;
    public LoanCreateDTO(){
    }
    public LoanCreateDTO(Integer maxamount, List<Integer> payment, String name,Double fees) {
        this.name = name;
        this.maxAmount = maxamount;
        this.payment = payment;
        this.fees= fees;
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
