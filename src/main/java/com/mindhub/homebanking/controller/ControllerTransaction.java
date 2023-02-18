package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
@RestController
@RequestMapping("/api")
public class ControllerTransaction {
    @Autowired

    private TransactionRepository transactionRepository;
    @RequestMapping("/transaction")
    public List<TransactionDTO> getAll() {
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(toList());
    }
    @RequestMapping("transaction/{id}")

    public TransactionDTO getTransaction(@PathVariable Long id){

        return transactionRepository.findById(id).map(transaction ->new TransactionDTO(transaction)).orElse(null);

    }
}
