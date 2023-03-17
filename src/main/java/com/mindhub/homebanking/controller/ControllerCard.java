package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.CardDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.mindhub.homebanking.utilities.Utils.NumberCards;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class ControllerCard {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/client/current/card")
    public List<CardDTO> listcards(Authentication authentication){
        Client client=clientRepository.findByEmail(authentication.getName());
        return client.getCards().stream().filter(active->active.getState()==State.ACTIVE).map(CardDTO::new).collect(toList());
    }
    @PatchMapping("/delete/card")
    public ResponseEntity<Object> deletecard(Authentication authentication,@RequestParam String number){
        Client client=clientRepository.findByEmail(authentication.getName());
        Card carddelete=(Card) client.getCards().stream().filter(card->card.getNumber()==number);
        if (carddelete!=null){
            return new ResponseEntity<>("that card does not exist", HttpStatus.BAD_REQUEST);
        }
        if (carddelete.getState()==State.DESACTIVE){
            return new ResponseEntity<>("your card is already deleted", HttpStatus.BAD_REQUEST);
        }
        carddelete.setState(State.DESACTIVE);
        cardRepository.save(carddelete);
        return new ResponseEntity<>("your card has been deleted successfully",HttpStatus.ACCEPTED);
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> newCard(
            Authentication authentication,
            @RequestParam TypeCard typeCard,
            @RequestParam ColorCard colorCard){
        Client client=clientRepository.findByEmail(authentication.getName());
        if(client.getCards().stream().filter(card -> card.getType()==typeCard).collect(toSet()).size()>=3){
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