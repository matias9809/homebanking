package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.mindhub.homebanking.utilities.Utils.NumberCards;


@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEnconder;
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientrepository, AccountRepository repoaccount, TransactionRepository transactionRepository, ClientLoansRepository repoclientLoans, LoansRepository repoLoans, CardRepository cardRepository) {
		return (args) -> {
			Client ADMI=new Client("admin","admin","ADMI@HOTMAIL.COM",passwordEnconder.encode("admi123"));
			Client client=new Client("Morel", "Melba","melba@mindhub.com",passwordEnconder.encode("melba123"));
			Account VIN001=new Account("VIN001",LocalDateTime.now(),5000.0);
			Account VIN002 =new Account("VIN002",LocalDateTime.now().plusDays(1) ,7500.0);

			Client client2=new Client("fulanito", "cosme","cosf@mindhub.com",passwordEnconder.encode("cosme123"));

			Account VIN003=new Account("VIN003",LocalDateTime.now(),25000.0);
			Account VIN004 =new Account("VIN004",LocalDateTime.now().plusDays(1),17500.0);
			Account VIN005=new Account("VIN005",LocalDateTime.now().plusDays(3) ,25000.0);

			Transaction trans1=new Transaction(TransactionType.CREDIT,5000.00,
					"accreditation of assets",LocalDateTime.now().plusDays(2));
			Transaction trans2=new Transaction(TransactionType.DEBIT,-3000.00,"fueling",LocalDateTime.now().plusDays(3));
			Transaction trans3=new Transaction(TransactionType.CREDIT,15000.00,
					"accreditation of assets",LocalDateTime.now().plusDays(2));
			Transaction trans4=new Transaction(TransactionType.DEBIT,-6000.00,"fueling",LocalDateTime.now().plusDays(3));
			Transaction trans5=new Transaction(TransactionType.CREDIT,30000.00,
					"accreditation of assets",LocalDateTime.now().plusDays(2));
			Transaction trans6=new Transaction(TransactionType.DEBIT,-25000.00,"fueling",LocalDateTime.now().plusDays(3));

			Loan mortage=new Loan("Mortgage",500000,Arrays.asList(12,24,36,48,60));
			Loan automotive=new Loan("Automotive",300000, Arrays.asList(6,12,24,36));
			Loan personal=new Loan("Personal",100000,Arrays.asList(6,12,24));

			ClientLoan clientLoan=new ClientLoan(12,50000);
			ClientLoan clientLoan2=new ClientLoan(60,400000);
			ClientLoan clientLoan3=new ClientLoan(24,100000);
			ClientLoan clientLoan4=new ClientLoan(36,200000);

			Card card1=new Card(client.getLastname()+" "+client.getFirstname(), TypeCard.DEBIT, ColorCard.GOLD,NumberCards(cardRepository)
					,LocalDate.now(), LocalDate.now().plusYears(5));
			Card card2=new Card(client.getLastname()+" "+client.getFirstname(), TypeCard.CREDIT, ColorCard.TITANIUM,NumberCards(cardRepository)
					,LocalDate.now(), LocalDate.now().plusYears(5));
			Card card3=new Card(client.getLastname()+" "+client.getFirstname(), TypeCard.CREDIT, ColorCard.SILVER,NumberCards(cardRepository)
					,LocalDate.now(), LocalDate.now().plusYears(5));



			client.addAccounts(VIN001);
			client.addAccounts(VIN002);
			client2.addAccounts(VIN003);
			client2.addAccounts(VIN004);
			client2.addAccounts(VIN005);


			VIN001.addTransaction(trans1);
			VIN001.addTransaction(trans2);
			VIN002.addTransaction(trans3);
			VIN005.addTransaction(trans4);
			VIN003.addTransaction(trans5);
			VIN004.addTransaction(trans6);

			client.addClientLoan(clientLoan);
			client.addClientLoan(clientLoan2);
			personal.addClientLoans(clientLoan);
			mortage.addClientLoans(clientLoan2);

			client2.addClientLoan(clientLoan3);
			client2.addClientLoan(clientLoan4);
			personal.addClientLoans(clientLoan3);
			automotive.addClientLoans(clientLoan4);

			client.addCard(card1);
			client.addCard(card2);
			client2.addCard(card3);

			repoLoans.save(mortage);
			repoLoans.save(automotive);
			repoLoans.save(personal);

			clientrepository.save(client);
			clientrepository.save(client2);

			repoaccount.save(VIN001);
			repoaccount.save(VIN002);
			repoaccount.save(VIN003);
			repoaccount.save(VIN004);
			repoaccount.save(VIN005);

			transactionRepository.save(trans1);
			transactionRepository.save(trans2);
			transactionRepository.save(trans3);
			transactionRepository.save(trans4);
			transactionRepository.save(trans5);
			transactionRepository.save(trans6);

			repoclientLoans.save(clientLoan);
			repoclientLoans.save(clientLoan2);
			repoclientLoans.save(clientLoan3);
			repoclientLoans.save(clientLoan4);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
		};
	}

}
