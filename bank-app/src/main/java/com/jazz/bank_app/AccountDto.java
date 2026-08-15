package com.jazz.bank_app;

public class AccountDto {
    private String accountHolder;
    private double amount;

    public AccountDto() {
    }

    // SETTERS
    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // GETTERS
    public String getAccountHolder() {
        return accountHolder;
    }

    public double getAmount() {
        return amount;
    }
}
