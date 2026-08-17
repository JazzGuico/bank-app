package com.jazz.bank_app;

import java.io.FileWriter;
import java.io.IOException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class BankAccount {
    // my  instance variable (located in heap memory. usable only by the methods in this class)
    private String accountHolder;
    protected double balance;
    @Transient
    private NotificationService notificationService; // new field
    @Id
    private int userId;

    // my constructor 
    protected BankAccount(String accountHolder, int userId, NotificationService notificationService) {
        if (accountHolder == null || accountHolder.trim().isEmpty()) {
        throw new IllegalArgumentException("Account holder name cannot be null or empty.");
        }
        if (notificationService == null) {
            throw new IllegalArgumentException("Notification service cannot be null.");
        }
        this.userId = userId;
        this.accountHolder = accountHolder;
        this.balance = 0;
        this.notificationService = notificationService; // stored once, used everywhere
    }

    protected BankAccount() {
        
    }

    // MY METHODS:
    // deposit method to add money to the account. returns true if deposit is successful, false otherwise
    public boolean deposit(double amount) {
        if (amount <= 0) { //ensures that every deposit amount is a positive number
            this.notificationService.sendNotification(this.accountHolder, "Deposit of " + amount + " failed. Amount must be positive.");
            return false;
        } else {
            this.balance += amount;
            this.notificationService.sendNotification(this.accountHolder, "Deposit of " + amount + " successful. New balance: " + this.balance);
            return true;
        }
    }

    // withdraw method to remove money from the account. returns true if withdrawal is successful, false otherwise
    public boolean withdraw(double amount) {
    if (amount <= 0 ) { //ensures that every withdrawal amount is a positive number
        this.notificationService.sendNotification(this.accountHolder, "Withdrawal of " + amount + " failed. Amount must be positive.");
        return false; // withdrawal denied
    } else if (amount > this.balance){ // ensures that the withdrawal amount does not exceed the current balance
        this.notificationService.sendNotification(this.accountHolder, "Withdrawal of " + amount + " failed. Insufficient funds. Current balance: " + this.balance);
        return false;
    }
    this.balance -= amount;
    this.notificationService.sendNotification(this.accountHolder, "Withdrawal of " + amount + " successful. New balance: " + this.balance);
    return true; // withdrawal succeeded. amount is deducted from balance
    }

    // method to generate a statement for the account
    public String generateStatement() {
        return "Account Holder: " + this.accountHolder + ", Balance: " + this.balance;
    }

    // method to return the values from methods (balance)
    public double getBalance() {
        return this.balance;
    }

    // method to return userId
    public int getUserId() {
        return this.userId;
    }

    // method to return the values from methods (accountHolder)
    public String getAccountHolder() {
        return this.accountHolder;
    }

    public void saveStatementToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(generateStatement());
        } catch (IOException e) {
            System.err.println("Error occurred while saving statement to file: " + e.getMessage());
        }
    }
}
