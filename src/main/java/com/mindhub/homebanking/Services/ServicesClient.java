package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Optional;

public interface ServicesClient {
    List<Client> findAll();
    Optional<Client> findById(Long id);
    void save(Client client);
    Client findByEmail(String email);

}
