package com.jazz.bank_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    private NotificationService notificationService;
    private Map<String, BankAccount> accounts = new HashMap<>();

    // Constructor to inject the NotificationService dependency
    @Autowired
    public AccountService(@Qualifier("emailNotificationService") NotificationService notificationService) { 
        this.notificationService = notificationService;
    }

    // Method to open a new bank account
    public BankAccount openAccount(String accountHolder) {
        BankAccount account = new BankAccount(accountHolder, notificationService);
        accounts.put(accountHolder, account);
        return account;
    }

    // Getter Method to retrieve an existing bank account by account holder's name
    public BankAccount getAccount(String accountHolder) {
        return accounts.get(accountHolder);
    }
}
