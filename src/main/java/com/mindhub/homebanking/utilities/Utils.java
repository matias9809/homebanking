package com.mindhub.homebanking.utilities;

import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;

public class Utils {
    public static String GenereteNumberRandomsAccount(){
        int number1=(int) (Math.random() * (99999999));
        String number ="VIN-"+number1;
        return number;
    }
    public static String AccountNumber(AccountRepository accountRepository){
        String Number;
        boolean verifyNumber;
        do {
            Number=GenereteNumberRandomsAccount();
            verifyNumber=accountRepository.existsByNumber(Number);
        }while(verifyNumber);
        return Number;
    }
    public static String GenereteNumberCardsRandoms(){
        int number1_1=(int) (Math.random() * (5- 4)+4);
        int number1=(int) (Math.random() * (999 - 100) + 100);
        int number2=(int) (Math.random() * (9999 - 1000) + 1000);
        int number3=(int) (Math.random() * (9999 - 1000) + 1000);
        int number4=(int) (Math.random() * (9999 - 1000) + 1000);
        String number =number1_1+""+number1+"-"+number2+"-"+number3+"-"+number4;
        return number;
    }
    public static String NumberCards(CardRepository cardRepo){
        String Number;
        boolean verifyNumber;
        do {
            Number=GenereteNumberCardsRandoms();
            verifyNumber=cardRepo.existsByNumber(Number);
        }while(verifyNumber);
        return Number;
    }
}
