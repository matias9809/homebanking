package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.ColorCard;
import com.mindhub.homebanking.models.TypeCard;

import java.time.LocalDate;

public class CardDTO {
    private Long id;
    private String cardholder;
    private TypeCard type;
    private ColorCard color;
    private String number;
    private short cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;

    public CardDTO(Card card){
        this.id=card.getId();
        this.cardholder=card.getCardholder();
        this.type=card.getType();
        this.color=card.getColor();
        this.number=card.getNumber();
        this.cvv=card.getCvv();
        this.fromDate=card.getFromDate();
        this.thruDate=card.getThruDate();
    }

    public Long getId() {
        return id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public TypeCard getType() {
        return type;
    }

    public ColorCard getColor() {
        return color;
    }

    public String getNumber() {
        return number;
    }

    public short getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }
}
