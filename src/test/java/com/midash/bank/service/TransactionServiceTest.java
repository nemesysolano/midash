package com.midash.bank.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.javafaker.Faker;
import com.midash.BussinessException;
import com.midash.bank.BaseServiceTest;
import com.midash.bank.model.Account;
import com.midash.bank.model.Customer;
import com.midash.bank.repository.AccountRepository;
import com.midash.bank.repository.CustomerRepository;


import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.TestInstance.Lifecycle;

@Slf4j
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionServiceTest extends BaseServiceTest {
    
    @Autowired
    TransactionService transactionService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

	Faker faker = new Faker();
	
    Customer customer1;
    
    Customer customer2;
    
    Account account1;

    Account account2;

    Customer createCustomer() {
		String customerId = RandomStringUtils.random(36, true, true);
		Customer customer = Customer.builder()
				.id(customerId)
				.firstName(faker.address().firstName())
				.lastName(faker.address().lastName())
				.phoneNumber(faker.phoneNumber().cellPhone())
				.build();
		
		customerRepository.save(customer);
        return customerRepository.findById(customerId).get();
	}

    Account createAccount(Customer customer, double balance) {
		
		Account account = Account.builder()
				.id(RandomStringUtils.random(36, true, true))
				.balance(balance)
				.customer(customer)
				.name(faker.artist().name())
				.build();      

        accountRepository.save(account);
        return accountRepository.findById(account.id).get();
    }

	@BeforeAll
    public void beforeAllTests() {
        customer1 = createCustomer();
        customer2 = createCustomer();
        account1 = createAccount(customer1, 101);
        account2 = createAccount(customer2, 100);
        log.debug("Fake customers and accounts created.");
    }	

    private void logAccountData(String message, Account account) {
        log.debug(
            String.format("%s -> description=%s, balance=%8.2f",  
            message,
            account.name,
            account.balance            
        ));
    }

    @Test
    @Order(1)
    public void validTransfer() {
        double withdrawAmount = 25.0;

        log.debug("validTransfer");

        logAccountData("account 1 before transfer", this.account1);
        logAccountData("account 2 before transfer", this.account2);
        assertTrue(account1.balance > account2.balance);        

        transactionService.transfer(account1.id, account2.id, withdrawAmount);
        
        Account account1Updated = transactionService.findById(account1.id);
        Account account2Updated = transactionService.findById(account2.id);

        assertTrue(account1Updated.balance < account2Updated.balance);

        logAccountData("account 1 after transfer", account1Updated);
        logAccountData("account 2 after transfer", account2Updated);        
    }	
    
    @Test
    @Order(2)
    public void transferWithInsuficcientfunds() {
        double withdrawAmount = 101.0;
        log.debug("transferWithInsuficcientfunds");

        Account account1 = transactionService.findById(this.account1.id);
        Account account2 = transactionService.findById(this.account2.id);

        try {
            logAccountData("account 1 before transfer", account1);
            logAccountData("account 2 before transfer", account2);
            assertTrue(account1.balance < account2.balance);
    
            transactionService.transfer(account1.id, account2.id, withdrawAmount);

        }catch(BussinessException cause) {
            log.debug("Transaction with underflow.");
            
        } finally {
            Account account1Updated = transactionService.findById(account1.id);
            Account account2Updated = transactionService.findById(account2.id);
    
            logAccountData("account 1 after transfer", account1Updated);
            logAccountData("account 2 after transfer", account2Updated);    
            assertTrue(account1Updated.balance < account2Updated.balance);    
        }
        

    }	

    @AfterAll
	public void afterAllTest() {
        accountRepository.deleteById(account1.id);
        accountRepository.deleteById(account2.id);
		customerRepository.deleteById(customer1.id);
        customerRepository.deleteById(customer2.id);
        log.debug("Fake customers and accounts removed.");
	}
}
