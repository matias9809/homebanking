package com.mindhub.homebanking.DTO;

public class LoanApplicationDTO {
    private Long id_prestamo;
    private Double amount;
    private Integer payment;
    private String numberAccount;
    public LoanApplicationDTO(){
    }
    public LoanApplicationDTO(Long id_prestamo, Double amount, Integer payment, String numberAccount) {
        this.id_prestamo = id_prestamo;
        this.amount = amount;
        this.payment = payment;
        this.numberAccount = numberAccount;
    }

    public Long getId_prestamo() {
        return id_prestamo;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayment() {
        return payment;
    }

    public String getNumberAccount() {
        return numberAccount;
    }
}
