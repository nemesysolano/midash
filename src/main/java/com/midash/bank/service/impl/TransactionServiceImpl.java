package com.midash.bank.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.midash.bank.MidashException;
import com.midash.bank.model.Account;
import com.midash.bank.repository.AccountRepository;
import com.midash.bank.service.InsuficientFundsException;
import com.midash.bank.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(value = Transactional.TxType.REQUIRED)
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    
    @Autowired
    AccountRepository accountRepository;

    @Override    
    public void transfer(String sourceAccountId, String targetAccountId, double amount) throws MidashException {
        try {
            Account sourceAccount = accountRepository.findById(sourceAccountId).get();
            Account targetAccount = accountRepository.findById(targetAccountId).get();

            Account updatedSourceAccount = sourceAccount.toBuilder()
                .balance(sourceAccount.balance - amount)
                .build();
            
            Account targetSourceAccount = targetAccount.toBuilder()
                .balance(targetAccount.balance + amount)
                .build();

            accountRepository.save(updatedSourceAccount);
            accountRepository.save(targetSourceAccount);

            if(sourceAccount.balance < amount) {                
                throw new InsuficientFundsException( // This one is an application/bussiness exception as it is explicitly thrown by the app
                    String.format("Insuficient funds in %s (%8.2f < %8.2f)", sourceAccount.name, sourceAccount.balance, amount), 
                    log
                );
            }


        }catch(DataAccessException cause) {            
            throw new MidashException("Can't perform money transfer between accounts due to a database error", cause, log); //TechnicalException
        }catch(NoSuchElementException cause) {
            throw new MidashException("Source or target account does not exist.", cause, log); //TechnicalException
        }
    }

    @Override
    public void create(Account account) throws MidashException {
        try {
            accountRepository.save(account);
        }catch(DataAccessException cause) {
            throw new MidashException("Can't create account due to a database error.", cause, log);
        }
        
    }

    @Override
    public void delete(String accountId) throws MidashException {
        try {
            Optional<Account> result = accountRepository.findById(accountId);
            
            if(result.isPresent()) {
                accountRepository.delete(result.get());
            }
            
        }catch(DataAccessException | NoSuchElementException cause) {
            throw new MidashException("Can't delete account due to a database error.", cause, log);
        }
        
        
    }

    @Override
    public Account findById(String accountId) throws MidashException {
        try {
            Optional<Account> result = accountRepository.findById(accountId);

            if(result.isPresent()) {
                return result.get();
            }

            return null;
        }catch(DataAccessException cause) {
            throw new MidashException("Can't find account due to a database error.", cause, log);
        }
        
    }

}
