package com.mindhub.homebanking.DTO;

public class CardServicesDTO {
    private String number;
    private Short cvv;
    private  Double amount;
    private String description;

    public CardServicesDTO(String number, Short cvv, Double amount, String description) {
        this.number = number;
        this.cvv = cvv;
        this.amount = amount;
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public Short getCvv() {
        return cvv;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
