package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.AccountDTO;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ControllerAccount {
    @Autowired

    private AccountRepository AccountRepository;
    @RequestMapping("/account")
    public List<AccountDTO> getAll() {
        return AccountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }
    @RequestMapping("account/{id}")

    public AccountDTO getClient(@PathVariable Long id){

        return AccountRepository.findById(id).map(account ->new AccountDTO(account)).orElse(null);

    }
}
