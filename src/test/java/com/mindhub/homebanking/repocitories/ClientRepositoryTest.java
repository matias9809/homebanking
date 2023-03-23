//package com.mindhub.homebanking.repocitories;
//
//import com.mindhub.homebanking.models.Card;
//import com.mindhub.homebanking.models.Client;
//import com.mindhub.homebanking.repositories.ClientRepository;
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
//public class ClientRepositoryTest {
//    @Autowired
//    private ClientRepository clientRepository;
//
//
//
//    @Test
//
//    public void existClient(){
//
//        List<Client> clients = clientRepository.findAll();
//
//        assertThat(clients,is(not(empty())));
//
//    }
//
//
//
//    @Test
//
//    public void existnameClient(){
//
//        List<Client> clients = clientRepository.findAll();
//
//        assertThat(clients, hasItem(hasProperty("firstname", is("Melba"))));
//
//    }
//}
