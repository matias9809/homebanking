package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Loan;

import java.util.List;
import java.util.Optional;

public interface ServicesLoan {
    Boolean existsByName(String name);
    Optional<Loan> findById(Long Id);
    List<Loan> findAll();
    Boolean existsById(Long Id);
    void save(Loan loan);
}
