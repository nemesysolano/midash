package com.midash.bank.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.github.javafaker.Faker;
import com.midash.bank.BaseServiceTest;
import com.midash.bank.MockClient;
import com.midash.bank.dto.MoneyTransferRequest;
import com.midash.bank.model.Account;
import com.midash.bank.model.Customer;
import com.midash.bank.repository.AccountRepository;
import com.midash.bank.repository.CustomerRepository;
import com.midash.bank.service.InsufficientFundsException;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionControllerTest extends BaseServiceTest{
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

    @Test
    @Order(1)
    public void transferWithInsufficientFunds() throws Exception {
        ErrorResponse response = MockClient.post(
            mvc, 
            "/transaction/transfer", 
            MoneyTransferRequest.builder()
                .sourceAccountId(account1.id)
                .targetAccountId(account2.id)
                .amount(102.0f)
                .build(),
            ErrorResponse.class, 
            MockMvcResultMatchers.status().isBadRequest(),
            Map.of()
        );

        log.debug(response.description);
        assertEquals(response.cause, InsufficientFundsException.class.getSimpleName());
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
