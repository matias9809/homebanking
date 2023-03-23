//package com.mindhub.homebanking.repocitories;
//
//import com.mindhub.homebanking.models.Card;
//import com.mindhub.homebanking.models.Loan;
//import com.mindhub.homebanking.repositories.LoanRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.hamcrest.Matchers.is;
//
//@SpringBootTest
//public class LoanRepositoryTest {
//    @Autowired
//    private LoanRepository loanRepository;
//
//
//
//    @Test
//
//    public void existLoan(){
//
//        List<Loan> loans = loanRepository.findAll();
//
//        assertThat(loans,is(not(empty())));
//
//    }
//
//
//
//    @Test
//
//    public void existNameLoan(){
//
//        List<Loan> loans = loanRepository.findAll();
//
//        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
//
//    }
//}
