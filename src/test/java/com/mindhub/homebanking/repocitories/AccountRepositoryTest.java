//package com.mindhub.homebanking.repocitories;
//
//import com.mindhub.homebanking.models.Account;
//import com.mindhub.homebanking.repositories.AccountRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//@SpringBootTest
//public class AccountRepositoryTest {
//    @Autowired
//    private AccountRepository accountRepository;
//
//
//
//    @Test
//
//    public void existAccount(){
//
//        List<Account> accounts = accountRepository.findAll();
//
//        assertThat(accounts,is(not(empty())));
//
//    }
//
//
//
//    @Test
//
//    public void existnumberAccount(){
//
//        List<Account> accounts = accountRepository.findAll();
//
//        assertThat(accounts, hasItem(hasProperty("number", is("VIN001"))));
//
//    }
//}
