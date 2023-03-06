package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientLoansRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
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

    @RequestMapping("/transaction")
    public List<TransactionDTO> getAll() {
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(toList());
    }
    @Transactional
    @RequestMapping("/clients/current/transaction")
    public ResponseEntity<Object> getTransaction(Authentication authentication,
         @RequestParam Double amount, @RequestParam String numberOrigin,
         @RequestParam String numberRecep, @RequestParam String description){
        Client client=clientRepository.findByEmail(authentication.getName());

        Account accountOrigins=accountRepository.findByNumber(numberOrigin);
        Account accountrecepter=accountRepository.findByNumber(numberRecep);

        if (amount.isNaN()){
            return new ResponseEntity<>("Missing amount", HttpStatus.BAD_REQUEST);
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
        Transaction transactionOrigin=new Transaction(TransactionType.DEBIT,-amount,description+" "+numberOrigin,LocalDateTime.now());
        Transaction transactionRecepter=new Transaction(TransactionType.CREDIT,amount,description+" "+numberOrigin,LocalDateTime.now());
        accountOrigins.setBalance(accountOrigins.getBalance()-amount);
        accountrecepter.setBalance(accountrecepter.getBalance()+amount);
        accountOrigins.addTransaction(transactionOrigin);
        accountrecepter.addTransaction(transactionRecepter);
        accountRepository.save(accountOrigins);
        accountRepository.save(accountrecepter);
        transactionRepository.save(transactionOrigin);
        transactionRepository.save(transactionRecepter);
        return new ResponseEntity<>("transaction completed correctly",HttpStatus.CREATED);
    }
}
