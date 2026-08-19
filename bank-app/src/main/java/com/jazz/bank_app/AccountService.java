package com.jazz.bank_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private NotificationService notificationService;
    private BankAccountRepository bankAccountRepository;

    // Constructor to inject the NotificationService dependency
    @Autowired
    public AccountService(@Qualifier("emailNotificationService") NotificationService notificationService, BankAccountRepository bankAccountRepository) { 
        this.notificationService = notificationService;
        this.bankAccountRepository = bankAccountRepository;
    }

    // Method to open a new bank account. Returns true if the account is successfully created, false if the account already exists.
    public boolean openAccount(String accountHolder) {
        if (bankAccountRepository.existsByAccountHolder(accountHolder)) {
            return false; // Account already exists
        }
        BankAccount account = new BankAccount(accountHolder);
        bankAccountRepository.save(account);
        return true; // Account successfully created 
    }

    // Getter Method to retrieve an existing bank account by account holder's name. to read the account details. Returns the BankAccount object if found, null if the account does not exist.
    public BankAccount getAccount(String accountHolder) {
        Optional<BankAccount> account = bankAccountRepository.findByAccountHolder(accountHolder);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new IllegalArgumentException("Account not found for holder: " + accountHolder);
        }
    }

    // Method to deposit money into an account (to update the account balance). Returns true if the deposit is successful, false otherwise.
    public boolean deposit(String accountHolder, double amount) {
        BankAccount account = getAccount(accountHolder);
        BankAccount.DepositResult success = account.deposit(amount);
        if (success == BankAccount.DepositResult.INVALID_AMOUNT) {
            this.notificationService.sendNotification(accountHolder, "Deposit of " + amount + " failed. Amount must be positive.");
            return false;
        } else {
            this.notificationService.sendNotification(accountHolder, "Deposit of " + amount + " successful. New balance: " + account.getBalance());
        }
        bankAccountRepository.save(account);
        return true;
    }

    // Method to withdraw money from an account (to update the account balance). Returns true if the withdrawal is successful, false otherwise.
    public boolean withdraw(String accountHolder, double amount) {
        BankAccount account = getAccount(accountHolder);
        BankAccount.WithdrawResult success = account.withdraw(amount);
        if (success == BankAccount.WithdrawResult.INVALID_AMOUNT) {
            this.notificationService.sendNotification(accountHolder, "Withdrawal of " + amount + " failed. Amount must be positive.");
            return false;
        } else if (success == BankAccount.WithdrawResult.INSUFFICIENT_FUNDS) {
            this.notificationService.sendNotification(accountHolder, "Withdrawal of " + amount + " failed. Insufficient funds. Current balance: " + account.getBalance());
            return false;
        } else {
            this.notificationService.sendNotification(accountHolder, "Withdrawal of " + amount + " successful. New balance: " + account.getBalance());
        }
        bankAccountRepository.save(account);
        return true;
    }

    // Method to create an account with initial deposit from AccountDto
    public boolean createAccount(AccountDto accountDto) {
        boolean accountOpened = openAccount(accountDto.getAccountHolder());
        if (!accountOpened) {
            return false; // Account already exists
        }
        if (accountDto.getAmount() == 0) {
            return true; // Account created with zero initial deposit
        } else if (accountDto.getAmount() > 0) {
            return deposit(accountDto.getAccountHolder(), accountDto.getAmount());
        } else {
            return false; // Invalid initial deposit amount
        }
    }
}
