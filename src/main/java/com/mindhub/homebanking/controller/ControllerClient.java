package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.Services.ServicesAccount;
import com.mindhub.homebanking.Services.ServicesClient;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utilities.Utils.AccountNumber;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class ControllerClient {
    @Autowired
    private ServicesClient servicesClient;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ServicesAccount servicesAccount;

    @GetMapping("/clients")
    public List<ClientDTO> getAll() {
        return servicesClient.findAll().stream().map(ClientDTO::new).collect(toList());
    }
    @GetMapping("clients/{id}")
    public Optional<ClientDTO> getClient(@PathVariable Long id){
        return servicesClient.findById(id).map(client ->new ClientDTO(client));
    }
    @GetMapping("/clients/current")
    public ClientDTO getClientAuthentic(Authentication authentication) {
        Client client=servicesClient.findByEmail(authentication.getName());
        Set<Account> account=client.getAccount().stream().filter(account1 -> account1.getState()== State.ACTIVE).collect(toSet());
        Set<Card> card=client.getCards().stream().filter(card1 -> card1.getState()==State.ACTIVE).collect(toSet());
        client.setAccount(account);
        client.setCards(card);
        return new ClientDTO(client);
    }
    @PostMapping("/clients")
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password, @RequestParam TypeAccount typeAccount) {

        if (firstName.isEmpty()) {
            return new ResponseEntity<>("Missing firstname", HttpStatus.BAD_REQUEST);
        }
        else if (lastName.isEmpty()) {
            return new ResponseEntity<>("Missing lastname", HttpStatus.BAD_REQUEST);
        } else if (email.isEmpty() ) {
            return new ResponseEntity<>("Missing email", HttpStatus.BAD_REQUEST);
        } else if (password.isEmpty()) {
            return new ResponseEntity<>("Missing password", HttpStatus.BAD_REQUEST);
        }

        if (servicesClient.findByEmail(email) !=  null) {

            return new ResponseEntity<>("email already in use", HttpStatus.BAD_REQUEST);

        }

        Client clientNewUser=new Client(firstName, lastName, email, passwordEncoder.encode(password));

        Account accountNewUser=new Account(AccountNumber(servicesAccount), LocalDateTime.now(),0.00,typeAccount);
        clientNewUser.addAccounts(accountNewUser);
        servicesClient.save(clientNewUser);
        servicesAccount.save(accountNewUser);
        return new ResponseEntity<>("user created successfully",HttpStatus.CREATED);

    }



}
