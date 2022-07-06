package com.midash.bank.service;

import org.slf4j.Logger;

import com.midash.BusinessException;

public class InsufficientFundsException extends BusinessException {
    double balance;
    double amount;

    public InsufficientFundsException(double balance, double amount, Logger logger) {
        super(String.format("Insuficient funds. Balance=%8.2f, Withdraw Amount=%8.2f", balance, amount), logger);     
        this.balance = balance;
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public double getAmount() {
        return amount;
    }
    
    
}
