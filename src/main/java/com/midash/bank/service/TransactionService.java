package com.midash.bank.service;

import com.midash.bank.MidashException;
import com.midash.bank.model.Account;


public interface TransactionService {
    public void transfer(String sourceAccountId, String targetAccountId, double amount) throws MidashException;
    public void create(Account account) throws MidashException;
    public void delete(String accountId) throws MidashException;
    public Account findById(String accountId) throws MidashException;
}
