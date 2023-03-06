package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.AccountDTO;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.utilities.Utils.AccountNumber;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ControllerAccount {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/account")
    public List<AccountDTO> getAll() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }
    @RequestMapping("account/{id}")

    public AccountDTO getClient(@PathVariable Long id){

        return accountRepository.findById(id).map(account ->new AccountDTO(account)).orElse(null);

    }
    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> newAccount (Authentication authentication) {
        Client client=clientRepository.findByEmail(authentication.getName());
        if (client.getAccount().size()>=3) {
            return new ResponseEntity<>("It has already reached the limit of 3 accounts", HttpStatus.BAD_REQUEST);
        }
        Account newAccount= new Account(AccountNumber(accountRepository), LocalDateTime.now(),0.00);
        client.addAccounts(newAccount);
        accountRepository.save(newAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
