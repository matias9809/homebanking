package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.AccountDTO;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.*;
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

    @GetMapping("/client/current/account")
    public List<AccountDTO> getaccountclient(Authentication authentication) {
        Client client=clientRepository.findByEmail(authentication.getName());
        return client.getAccount().stream().filter(active->active.getState()== State.ACTIVE).map(AccountDTO::new).collect(toList());
    }
    @GetMapping("/account")
    public List<AccountDTO> getAll(Authentication authentication) {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }
    @GetMapping("account/{id}")

    public AccountDTO getClient(@PathVariable Long id){

        return accountRepository.findById(id).map(account ->new AccountDTO(account)).orElse(null);

    }
    @PatchMapping("/delete/account")
    public ResponseEntity<Object> deletecard(Authentication authentication, String number){
        Client client=clientRepository.findByEmail(authentication.getName());
        Account accountdelete=(Account) client.getCards().stream().filter(delete->delete.getNumber()==number);
        if (accountdelete!=null){
            return new ResponseEntity<>("that card does not exist", HttpStatus.BAD_REQUEST);
        }
        if (accountdelete.getState()==State.DESACTIVE){
            return new ResponseEntity<>("your card is already deleted", HttpStatus.BAD_REQUEST);
        }
        accountdelete.setState(State.DESACTIVE);
        accountRepository.save(accountdelete);
        return new ResponseEntity<>("your card has been deleted successfully", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount (Authentication authentication,TypeAccount typeAccount) {
        Client client=clientRepository.findByEmail(authentication.getName());
        if (client.getAccount().size()>=3) {
            return new ResponseEntity<>("It has already reached the limit of 3 accounts", HttpStatus.BAD_REQUEST);
        }
        Account newAccount= new Account(AccountNumber(accountRepository), LocalDateTime.now(),0.00,typeAccount);
        client.addAccounts(newAccount);
        accountRepository.save(newAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
