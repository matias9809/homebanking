package com.mindhub.homebanking.Services.ServicesImpl;

import com.mindhub.homebanking.Services.ServicesLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanServicesImpl implements ServicesLoan {
    @Autowired
    private LoanRepository loanRepository;
    @Override
    public Boolean existsByName(String name) {
        return loanRepository.existsByName(name);
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id);
    }

    @Override
    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    @Override
    public Boolean existsById(Long id) {
        return loanRepository.existsById(id);
    }

    @Override
    public void save(Loan loan) {
        loanRepository.save(loan);
    }
}
