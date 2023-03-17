package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.CardServicesDTO;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
@RestController
@RequestMapping("/api")
public class ControllerTransaction {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/transaction")
    public List<TransactionDTO> getAll() {
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(toList());
    }
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> getTransaction(Authentication authentication,
         @RequestParam(required = false) Double amount, @RequestParam String numberOrigin,
         @RequestParam String numberRecep, @RequestParam String description){
        Client client=clientRepository.findByEmail(authentication.getName());

        Account accountOrigins=accountRepository.findByNumber(numberOrigin);
        Account accountrecepter=accountRepository.findByNumber(numberRecep);

        if (amount.isNaN()||amount==null){
            return new ResponseEntity<>("Missing amount", HttpStatus.BAD_REQUEST);
        } else if (amount<0) {
            return new ResponseEntity<>("cannot transfer negative numbers", HttpStatus.BAD_REQUEST);
        } else if (numberOrigin.isEmpty()) {
            return new ResponseEntity<>("Missing number origin", HttpStatus.BAD_REQUEST);
        } else if (numberRecep.isEmpty()) {
            return new ResponseEntity<>("Missing number receptor", HttpStatus.BAD_REQUEST);
        } else if (description.isEmpty()) {
            return new ResponseEntity<>("Missing description", HttpStatus.BAD_REQUEST);
        }
        if (numberOrigin.equals(numberRecep)){
            return new ResponseEntity<>("cannot transfer to the same account", HttpStatus.BAD_REQUEST);
        }
        if (accountRepository.findByNumber(numberOrigin)==null){
            return new ResponseEntity<>("that origin account does not exist", HttpStatus.BAD_REQUEST);
        }
        if (client.getAccount().stream().noneMatch(account -> account.getNumber().equals(numberOrigin))){
            return new ResponseEntity<>("this account does not belong to you", HttpStatus.BAD_REQUEST);
        }
        if (accountRepository.findByNumber(numberRecep)==null){
            return new ResponseEntity<>("that receiving account does not exist", HttpStatus.BAD_REQUEST);
        }
        if (accountOrigins.getBalance()<amount){
            return new ResponseEntity<>("Insufficient fund to carry out the transaction", HttpStatus.BAD_REQUEST);
        }
        Transaction transactionOrigin=new Transaction(TransactionType.DEBIT,-amount,description+" "+numberOrigin,LocalDateTime.now(),accountOrigins.getBalance()-amount);
        Transaction transactionRecepter=new Transaction(TransactionType.CREDIT,amount,description+" "+numberOrigin,LocalDateTime.now(),accountrecepter.getBalance()+amount);
        accountOrigins.addTransaction(transactionOrigin);
        accountrecepter.addTransaction(transactionRecepter);
        accountOrigins.setBalance(accountOrigins.getBalance()-amount);
        accountrecepter.setBalance(accountrecepter.getBalance()+amount);
        transactionRepository.save(transactionOrigin);
        transactionRepository.save(transactionRecepter);
        accountRepository.save(accountOrigins);
        accountRepository.save(accountrecepter);

        return new ResponseEntity<>("transaction completed correctly",HttpStatus.CREATED);
    }
    @Transactional
    @PostMapping("/client/transaction/debit")
    public ResponseEntity<Object> transactionDebit(@RequestBody CardServicesDTO cardServicesDTO){
        Card card=cardRepository.findByNumber(cardServicesDTO.getNumber());
        Account account=(Account) card.getClient().getAccount().stream().filter(account1 -> account1.getBalance()>=cardServicesDTO.getAmount());
        if (cardServicesDTO.getNumber().isEmpty()){
            return new ResponseEntity<>("I did not enter the card number", HttpStatus.BAD_REQUEST);
        }
        if (cardServicesDTO.getAmount().isNaN()||cardServicesDTO.getAmount()==null){
            return new ResponseEntity<>("Missing amount", HttpStatus.BAD_REQUEST);
        }
        if (cardServicesDTO.getCvv()==null){
            return new ResponseEntity<>("Missing cvv", HttpStatus.BAD_REQUEST);
        }
        if (cardServicesDTO.getDescription().isEmpty()){
            return new ResponseEntity<>("Missing description", HttpStatus.BAD_REQUEST);
        }
        if(card==null){
            return new ResponseEntity<>("the card does not exist", HttpStatus.BAD_REQUEST);
        }
        if (account.getBalance()<cardServicesDTO.getAmount()||account==null){
            return new ResponseEntity<>("Insufficient fund to carry out the transaction", HttpStatus.BAD_REQUEST);
        }
        if (card.getType()==TypeCard.CREDIT){
            return new ResponseEntity<>("Missing description", HttpStatus.BAD_REQUEST);
        }
        if (card.getState()==State.DESACTIVE){
            return new ResponseEntity<>("this card is not valid", HttpStatus.BAD_REQUEST);
        }
        if (card.getCvv()!= cardServicesDTO.getCvv()){
            return new ResponseEntity<>("the data entered is incorrect", HttpStatus.BAD_REQUEST);
        }
        if (card.getThruDate().isAfter(LocalDate.now())){
            return new ResponseEntity<>("your card is expired", HttpStatus.BAD_REQUEST);
        }
        Transaction transaction=new Transaction(TransactionType.DEBIT, cardServicesDTO.getAmount(), cardServicesDTO.getDescription(), LocalDateTime.now(),account.getBalance()-cardServicesDTO.getAmount());
        account.addTransaction(transaction);
        account.setBalance(account.getBalance()-cardServicesDTO.getAmount());
        accountRepository.save(account);
        transactionRepository.save(transaction);
        return new ResponseEntity<>("transaction completed correctly",HttpStatus.CREATED);
    }
}
