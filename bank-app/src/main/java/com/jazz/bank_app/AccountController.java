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

    // Endpoint to retrieve an existing bank account by account holder's name (read)
    @GetMapping("/{accountHolder}")
    public BankAccount getAccount(@PathVariable String accountHolder) {
        return accountService.getAccount(accountHolder);
    }

    // Endpoint to open a new bank account (create)
    @PostMapping("/{accountHolder}")
    public boolean openAccount(@PathVariable String accountHolder) {
        return accountService.openAccount(accountHolder);
    }

    // Endpoint to deposit money into an account (update)
    @PutMapping("/{accountHolder}/deposit")
    public boolean deposit(@PathVariable String accountHolder, @RequestParam double amount) {
        return accountService.deposit(accountHolder, amount);
    }
    

    // Endpoint to withdraw money from an account (update)
    @PutMapping("/{accountHolder}/withdraw")
    public boolean withdraw(@PathVariable String accountHolder, @RequestParam double amount) {
        return accountService.withdraw(accountHolder, amount);
    }

    // Endpoint to deserialize JSON data and to create an account using the deserialized data (create)
    @PostMapping
    public boolean createAccount(@RequestBody AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }
}
    
