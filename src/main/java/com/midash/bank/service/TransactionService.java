package com.midash.bank.service;

import com.midash.TechnicalException;
import com.midash.bank.model.Account;


public interface TransactionService {
    public void transfer(String sourceAccountId, String targetAccountId, double amount) throws TechnicalException;
    public void create(Account account) throws TechnicalException;
    public void delete(String accountId) throws TechnicalException;
    public Account findById(String accountId) throws TechnicalException;
}
