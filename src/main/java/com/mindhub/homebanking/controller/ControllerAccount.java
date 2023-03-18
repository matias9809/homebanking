package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.AccountDTO;

import com.mindhub.homebanking.Services.ServicesAccount;
import com.mindhub.homebanking.Services.ServicesClient;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utilities.Utils.AccountNumber;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class ControllerAccount {
    @Autowired
    private ServicesAccount servicesAccount;
    @Autowired
    private ServicesClient servicesClient;

    @GetMapping("/client/current/account")
    public List<AccountDTO> getaccountclient(Authentication authentication) {
        Client client= servicesClient.findByEmail(authentication.getName());
        return client.getAccount().stream().filter(active->active.getState()== State.ACTIVE).map(AccountDTO::new).collect(toList());
    }
    @GetMapping("/account")
    public List<AccountDTO> getAll(Authentication authentication) {
        return servicesAccount.findAll().stream().map(AccountDTO::new).collect(toList());
    }
    @GetMapping("account/{id}")

    public AccountDTO getClient(@PathVariable Long id){

        return servicesAccount.findById(id).map(account ->new AccountDTO(account)).orElse(null);

    }
    @PatchMapping("/delete/account")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, String number){
        Client client= servicesClient.findByEmail(authentication.getName());
        Account accountdelete=servicesAccount.findByNumber(number);
        if (accountdelete==null){
            return new ResponseEntity<>("that account does not exist", HttpStatus.BAD_REQUEST);
        }
        if (accountdelete.getState()==State.DESACTIVE){
            return new ResponseEntity<>("your account is already deleted", HttpStatus.BAD_REQUEST);
        }
        if (client.getAccount().stream().filter(account -> account.getNumber().equals(number))==null){
            return new ResponseEntity<>("You cannot delete the account", HttpStatus.BAD_REQUEST);
        }
        if (client.getLoan().size()>=1&&client.getAccount().size()==1){
            return new ResponseEntity<>("You cannot delete the account since it is the only one associated and you still have a loan", HttpStatus.BAD_REQUEST);
        }
        if (accountdelete.getBalance()>0){
            return new ResponseEntity<>("You balance is positive", HttpStatus.BAD_REQUEST);
        }
        accountdelete.setState(State.DESACTIVE);
        servicesAccount.save(accountdelete);
        return new ResponseEntity<>("your card has been deleted successfully", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount (Authentication authentication,@RequestParam TypeAccount typeAccount) {
        Client client= servicesClient.findByEmail(authentication.getName());
        if (client.getAccount().stream().filter(account -> account.getState()==State.ACTIVE).collect(toSet()).size()>=3) {
            return new ResponseEntity<>("It has already reached the limit of 3 accounts", HttpStatus.BAD_REQUEST);
        }
        Account newAccount= new Account(AccountNumber(servicesAccount), LocalDateTime.now(),0.00,typeAccount);
        client.addAccounts(newAccount);
        servicesAccount.save(newAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
