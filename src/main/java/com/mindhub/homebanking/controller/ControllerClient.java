package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ControllerClient {
    @Autowired
    private ClientRepository clientrepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getAll() {
        return clientrepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }
    @RequestMapping("clients/{id}")
    public Optional<ClientDTO> getClient(@PathVariable Long id){
        return clientrepository.findById(id).map(client ->new ClientDTO(client));
    }
}
