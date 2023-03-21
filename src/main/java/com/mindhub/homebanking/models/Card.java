package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name="native",strategy = "native")
    private Long id;
    private String cardholder;
    private TypeCard type;
    private ColorCard color;
    private String number;
    private short cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private State state;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;


    public Card(){}
    public Card(String cardholder, TypeCard type, ColorCard color, String number, LocalDate thruDate, LocalDate fromDate){
        this.cardholder=cardholder;
        this.cvv=(short) (Math.random() * (999 - 100) + 100);
        this.type=type;
        this.color=color;
        this.fromDate=fromDate;
        this.thruDate=thruDate.plusYears(5);
        this.number=number;
        this.state=State.ACTIVE;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public TypeCard getType() {
        return type;
    }

    public void setType(TypeCard type) {
        this.type = type;
    }

    public ColorCard getColor() {
        return color;
    }

    public void setColor(ColorCard color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public short getCvv() {
        return cvv;
    }

    public void setCvv(short cvv) {
        this.cvv = cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
