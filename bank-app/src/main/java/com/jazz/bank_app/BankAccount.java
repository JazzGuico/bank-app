package com.jazz.bank_app;

import java.io.FileWriter;
import java.io.IOException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BankAccount {
    // my  instance variable (located in heap memory. usable only by the methods in this class)
    private String accountHolder;
    protected double balance;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    // my constructor 
    protected BankAccount(String accountHolder) {
        if (accountHolder == null || accountHolder.trim().isEmpty()) {
        throw new IllegalArgumentException("Account holder name cannot be null or empty.");
        }
        this.accountHolder = accountHolder;
        this.balance = 0;
    }

    protected BankAccount() {
        
    }

    // MY METHODS:
    // deposit method to add money to the account. returns true if deposit is successful, false otherwise
    public DepositResult deposit(double amount) {
        if (amount <= 0) { //ensures that every deposit amount is a positive number
            return DepositResult.INVALID_AMOUNT;
        } else {
            this.balance += amount;
            return DepositResult.SUCCESS;
        }
    }

    // withdraw method to remove money from the account. returns true if withdrawal is successful, false otherwise
    public WithdrawResult withdraw(double amount) {
    if (amount <= 0 ) { //ensures that every withdrawal amount is a positive number
        return WithdrawResult.INVALID_AMOUNT; // withdrawal denied
    } else if (amount > this.balance){ // ensures that the withdrawal amount does not exceed the current balance
        return WithdrawResult.INSUFFICIENT_FUNDS;
    }
    this.balance -= amount;
    return WithdrawResult.SUCCESS; // withdrawal succeeded. amount is deducted from balance
    }

    public enum DepositResult {
        SUCCESS,
        INVALID_AMOUNT
    }

    public enum WithdrawResult {
        SUCCESS, 
        INVALID_AMOUNT,
        INSUFFICIENT_FUNDS
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
    public Integer getUserId() {
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
