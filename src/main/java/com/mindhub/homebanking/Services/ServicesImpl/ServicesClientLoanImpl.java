package com.mindhub.homebanking.Services.ServicesImpl;

import com.mindhub.homebanking.Services.ServicesClientLoan;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesClientLoanImpl implements ServicesClientLoan {
    @Autowired
    private ClientLoansRepository clientLoansRepository;

    @Override
    public List<ClientLoan> findAll() {
        return null;
    }

    @Override
    public void save(ClientLoan clientLoan) {

    }
}
