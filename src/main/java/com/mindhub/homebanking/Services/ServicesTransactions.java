package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface ServicesTransactions {
List<Transaction> findAll();
void save(Transaction transaction);
}
