package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utilities.Utils.NumberCards;

@RestController
@RequestMapping("/api")
public class ControllerCard {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/client/current/card")
    public List<CardDTO> listadetarjetas(Authentication authentication){
        Client client=clientRepository.findByEmail(authentication.getName());
        return client.getCards().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> newCard(
            Authentication authentication,
            @RequestParam TypeCard typeCard,
            @RequestParam ColorCard colorCard){
        Client client=clientRepository.findByEmail(authentication.getName());
        if(client.getCards().stream().filter(card -> card.getType()==typeCard).collect(Collectors.toSet()).size()>=3){
            return new ResponseEntity<>("You have already reached the limit of 3 "+typeCard+" cards, you cannot be given another one", HttpStatus.BAD_REQUEST);
        }
        if (clientRepository.findByEmail(authentication.getName()).getCards().stream().anyMatch(card -> card.getColor()==colorCard&&card.getType()==typeCard)){
            return new ResponseEntity<>("you have already the same card", HttpStatus.BAD_REQUEST);
        }
        Card card = new Card(client.getFirstname()+" "+client.getLastname(), typeCard, colorCard,NumberCards(cardRepository) , LocalDate.now(),LocalDate.now().plusYears(5));
        clientRepository.findByEmail(authentication.getName()).addCard(card);
        cardRepository.save(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}