package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.Services.*;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ControllerLoan {
    @Autowired
    private ServicesLoan servicesLoan;

    @Autowired
    private ServicesClient servicesClient;

    @Autowired
    private ServicesAccount servicesAccount;

    @Autowired
    private ServicesClientLoan servicesClientLoan;

    @Autowired
    private ServicesTransactions servicesTransactions;

    @GetMapping("/Loans")
    public List<LoanDTO> getAll() {
        return servicesLoan.findAll().stream().map(LoanDTO::new).collect(toList());
    }
    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> getLoans(Authentication authentication,
             @RequestBody LoanApplicationDTO loanApplicationDTO){
            Client client= servicesClient.findByEmail(authentication.getName());
            List<LoanDTO> loans= servicesLoan.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
            Loan loan= servicesLoan.findById(loanApplicationDTO.getId_prestamo()).orElse(null);
            Account accountreciving= servicesAccount.findByNumber(loanApplicationDTO.getNumberAccount());


            if (loanApplicationDTO.getAmount().isNaN()||loanApplicationDTO.getAmount()==null){
                return new ResponseEntity<>("Missing amount", HttpStatus.BAD_REQUEST);
            }
            if (loanApplicationDTO.getNumberAccount().isEmpty()) {
                return new ResponseEntity<>("Missing number origin", HttpStatus.BAD_REQUEST);
            }
            if (loanApplicationDTO.getPayment()<1) {
                return new ResponseEntity<>("number of installments not allowed", HttpStatus.BAD_REQUEST);
            }
            if(loanApplicationDTO.getAmount()<=0){
                return new ResponseEntity<>("cannot transfer negative amount", HttpStatus.BAD_REQUEST);
            }
            if (!servicesLoan.existsById(loanApplicationDTO.getId_prestamo())){
                return new ResponseEntity<>("the requested loan does not exist", HttpStatus.BAD_REQUEST);
            }
            if (servicesLoan.findById(loanApplicationDTO.getId_prestamo()).orElse(null).getMaxAmount()<loanApplicationDTO.getAmount()){
                return new ResponseEntity<>("exceeds the maximum amount available to request", HttpStatus.BAD_REQUEST);
            }
            if (!servicesLoan.findById(loanApplicationDTO.getId_prestamo()).orElse(null).getPayment().contains(loanApplicationDTO.getPayment())){
                return new ResponseEntity<>("the payment option is incorrect", HttpStatus.BAD_REQUEST);
            }
            if (servicesAccount.findByNumber(loanApplicationDTO.getNumberAccount())==null){
                return new ResponseEntity<>("that origin account does not exist", HttpStatus.BAD_REQUEST);
            }
            if (client.getAccount().stream().noneMatch(account -> account.getNumber().equals(loanApplicationDTO.getNumberAccount()))){
                return new ResponseEntity<>("this account does not belong to you", HttpStatus.BAD_REQUEST);
            }
            if (client.getLoan().contains(servicesLoan.findById(loanApplicationDTO.getId_prestamo()).orElse(null))){
                return new ResponseEntity<>("you already have this loan", HttpStatus.BAD_REQUEST);
            }
            ClientLoan clientLoan=new ClientLoan(loanApplicationDTO.getPayment(), loanApplicationDTO.getAmount()*loan.getFees());
            client.addClientLoan(clientLoan);
            loan.addClientLoans(clientLoan);
            Transaction transaction=new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(),loan.getName()+" loan", LocalDateTime.now(),accountreciving.getBalance()+loanApplicationDTO.getAmount());
            accountreciving.addTransaction(transaction);
            accountreciving.setBalance(accountreciving.getBalance()+loanApplicationDTO.getAmount());
            servicesClientLoan.save(clientLoan);
            servicesClient.save(client);
            servicesLoan.save(loan);
            servicesTransactions.save(transaction);
            servicesAccount.save(accountreciving);
            return new ResponseEntity<>("loan request correctly",HttpStatus.CREATED);
    }
    @PostMapping("/create/loan")
    public ResponseEntity<Object> createloan(Authentication authentication, @RequestParam Integer maxamount,@RequestParam List<Integer> payment,
                                             @RequestParam String name,@RequestParam Double fees){
        Client Admin= servicesClient.findByEmail(authentication.getName());
        if (fees.isNaN()||fees==null){
            return new ResponseEntity<>("Missing fees", HttpStatus.BAD_REQUEST);
        }
       if (maxamount<1){
            return new ResponseEntity<>("amount must be greater than 1", HttpStatus.BAD_REQUEST);
        }
       if (maxamount==null){
           return new ResponseEntity<>("missing amount", HttpStatus.BAD_REQUEST);
       }
        if (payment==null){
            return new ResponseEntity<>("Missing payment", HttpStatus.BAD_REQUEST);
        }
        if (payment.stream().anyMatch(payments->payments<1)){
            return new ResponseEntity<>("odds must be greater than 1", HttpStatus.BAD_REQUEST);
        }
        if (name.isEmpty()){
            return new ResponseEntity<>("Missing name", HttpStatus.BAD_REQUEST);
        }
        if(servicesLoan.existsByName(name)){
            return new ResponseEntity<>("that loan already exists", HttpStatus.BAD_REQUEST);
        }
        Loan loan=new Loan(name,maxamount,payment,fees);
        servicesLoan.save(loan);
        return new ResponseEntity<>("loan created correctly",HttpStatus.CREATED);
    }
}
