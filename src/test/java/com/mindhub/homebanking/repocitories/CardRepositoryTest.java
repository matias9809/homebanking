//package com.mindhub.homebanking.repocitories;
//
//
//import com.mindhub.homebanking.models.Card;
//import com.mindhub.homebanking.repositories.CardRepository;
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
//public class CardRepositoryTest {
//    @Autowired
//    private CardRepository cardRepository;
//
//
//
//    @Test
//
//    public void existCard(){
//
//        List<Card> cards = cardRepository.findAll();
//
//        assertThat(cards,is(not(empty())));
//
//    }
//
//
//
//    @Test
//
//    public void existCardHolder(){
//
//        List<Card> cards = cardRepository.findAll();
//
//        assertThat(cards, hasItem(hasProperty("cardholder", is("Morel Melba"))));
//
//    }
//}
