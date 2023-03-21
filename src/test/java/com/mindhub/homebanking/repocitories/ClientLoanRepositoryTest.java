package com.mindhub.homebanking.repocitories;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.ClientLoansRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class ClientLoanRepositoryTest {
    @Autowired
    private ClientLoansRepository clientLoansRepository;



    @Test

    public void existClientLoan(){

        List<ClientLoan> clientLoans = clientLoansRepository.findAll();

        assertThat(clientLoans,is(not(empty())));

    }



    @Test

    public void existamountClientLoan(){

        List<ClientLoan> clientLoans = clientLoansRepository.findAll();

        assertThat(clientLoans, hasItem(hasProperty("amount", is(greaterThan(1)))));

    }
}
