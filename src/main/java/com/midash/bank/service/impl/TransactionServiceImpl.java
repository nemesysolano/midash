package com.midash.bank.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.midash.BussinessException;
import com.midash.TechnicalException;
import com.midash.bank.model.Account;
import com.midash.bank.repository.AccountRepository;
import com.midash.bank.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(value = Transactional.TxType.REQUIRED)
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    
    @Autowired
    AccountRepository accountRepository;

    @Override    
    public void transfer(String sourceAccountId, String targetAccountId, double amount) throws TechnicalException {
        try {
            Account sourceAccount = accountRepository.findById(sourceAccountId).get();
            Account targetAccount = accountRepository.findById(targetAccountId).get();
            double sourceBalance = sourceAccount.balance;
            double targetBalance = targetAccount.balance;

            Account updatedSourceAccount = sourceAccount.toBuilder()
                .balance(sourceBalance - amount)
                .build();
            
            Account targetSourceAccount = targetAccount.toBuilder()
                .balance(targetBalance + amount)
                .build();

            accountRepository.save(updatedSourceAccount);
            accountRepository.save(targetSourceAccount);

            if(sourceAccount.balance < amount) {                
                throw new BussinessException( // This one is an application/bussiness exception as it is explicitly thrown by the app
                    String.format("Insuficient funds in %s (%8.2f < %8.2f)", sourceAccount.name, sourceBalance, amount), //
                    log
                );
            }

            //TechnicalException wraps exception thrown by third party libraries (spring, jpa, apache commons) or JDK (java.io.Exception).
        }catch(DataAccessException cause) { 
            throw new TechnicalException("Can't perform money transfer between accounts due to a database error", cause, log); 
        }catch(NoSuchElementException cause) {
            throw new TechnicalException("Source or target account does not exist.", cause, log); 
        }
    }

    @Override
    public void create(Account account) throws TechnicalException {
        try {
            accountRepository.save(account);
        }catch(DataAccessException cause) {
            throw new TechnicalException("Can't create account due to a database error.", cause, log);
        }
        
    }

    @Override
    public void delete(String accountId) throws TechnicalException {
        try {
            Optional<Account> result = accountRepository.findById(accountId);
            
            if(result.isPresent()) {
                accountRepository.delete(result.get());
            }
            
        }catch(DataAccessException | NoSuchElementException cause) {
            throw new TechnicalException("Can't delete account due to a database error.", cause, log);
        }
        
        
    }

    @Override
    public Account findById(String accountId) throws TechnicalException {
        try {
            Optional<Account> result = accountRepository.findById(accountId);

            if(result.isPresent()) {
                return result.get();
            }

            return null;
        }catch(DataAccessException cause) {
            throw new TechnicalException("Can't find account due to a database error.", cause, log);
        }
        
    }

}
