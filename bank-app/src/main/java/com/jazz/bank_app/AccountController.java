package com.jazz.bank_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts") // Base URL for account-related endpoints
public class AccountController {
    // Dependency on AccountService to handle account operations
    private final AccountService accountService;

    // Constructor to inject the AccountService dependency
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Endpoint to retrieve an existing bank account by account holder's name
    @GetMapping("/{accountHolder}")
    public BankAccount getAccount(@PathVariable String accountHolder) {
        return accountService.getAccount(accountHolder);
    }

    // Endpoint to open a new bank account
    @PostMapping("/{accountHolder}")
    public BankAccount openAccount(@PathVariable String accountHolder) {
        return accountService.openAccount(accountHolder); 
    }

    // Endpoint to deposit money into an account
    @PutMapping("/{accountHolder}/deposit")
    public boolean deposit(@PathVariable String accountHolder, @RequestParam double amount) {
    BankAccount account = accountService.getAccount(accountHolder);
    if (account != null) {
        return account.deposit(amount);
    } else {
        throw new IllegalArgumentException("Account not found for holder: " + accountHolder);
    }
}

    // Endpoint to withdraw money from an account
    @PutMapping("/{accountHolder}/withdraw")
    public boolean withdraw(@PathVariable String accountHolder, @RequestParam double amount) {
        BankAccount account = accountService.getAccount(accountHolder);
        if (account != null) {
            return account.withdraw(amount);
        } else {
            throw new IllegalArgumentException("Account not found for holder: " + accountHolder);
        }
    }
}
