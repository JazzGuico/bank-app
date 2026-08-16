package com.jazz.bank_app;

public class AccountResponse {
    private String accountHolder;
    private double balance;

    public AccountResponse(String accountHolder, double balance) {
        // constructor that takes both values directly
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    // GETTERS
    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }
}
