package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.TypeAccount;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.mindhub.homebanking.utilities.Utils.AccountNumber;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ControllerClient {
    @Autowired
    private ClientRepository clientrepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/clients")
    public List<ClientDTO> getAll() {
        return clientrepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }
    @GetMapping("clients/{id}")
    public Optional<ClientDTO> getClient(@PathVariable Long id){
        return clientrepository.findById(id).map(client ->new ClientDTO(client));
    }
    @GetMapping("/clients/current")
    public ClientDTO getClientAuthentic(Authentication authentication) {
        return new ClientDTO(clientrepository.findByEmail(authentication.getName()));
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

        if (clientrepository.findByEmail(email) !=  null) {

            return new ResponseEntity<>("email already in use", HttpStatus.BAD_REQUEST);

        }

        Client clientNewUser=new Client(firstName, lastName, email, passwordEncoder.encode(password));

        Account accountNewUser=new Account(AccountNumber(accountRepository), LocalDateTime.now(),0.00,typeAccount);
        clientNewUser.addAccounts(accountNewUser);
        clientrepository.save(clientNewUser);
        accountRepository.save(accountNewUser);
        return new ResponseEntity<>("user created successfully",HttpStatus.CREATED);

    }



}
