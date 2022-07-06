package com.midash.bank.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.javafaker.Faker;
import com.midash.bank.BaseServiceTest;
import com.midash.bank.model.Account;
import com.midash.bank.model.Customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountRepositoryTest extends BaseServiceTest{
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	String customerId;    
	String accountId;	
	String accountName1;
	String accountName2;
	
	Faker faker = new Faker();
	
	@BeforeAll
    public void beforeAllTests() {
		customerId = RandomStringUtils.random(36, true, true);
		accountId = RandomStringUtils.random(36, true, true);
		accountName1 = faker.beer().yeast();
		accountName2 = faker.beer().yeast();
		Customer customer = Customer.builder()
				.id(customerId)
				.firstName(faker.address().firstName())
				.lastName(faker.address().lastName())
				.phoneNumber(faker.phoneNumber().cellPhone())
				.build();
		
		customerRepository.save(customer);
		Optional<Customer> response = customerRepository.findById(customer.id);
		assertTrue(response.isPresent());		
    }	
	
	@Test
	@Order(1)
	public void testSaveAccount() {
		Customer customer = customerRepository.findById(customerId).get();
		Account account = Account.builder()
				.id(accountId)
				.balance(100)
				.customer(customer)
				.name(accountName1)
				.build();
		
		accountRepository.save(account);		
	}

	@Test
	@Order(2)
	public void testUpdateAccount() {
		Account account = accountRepository.findById(accountId).get();
		double increasedBalance = account.balance * 1.05;
		Account updatedAccount = account.toBuilder()
				.name(accountName2)
				.balance(increasedBalance)
				.build();
				
		accountRepository.save(updatedAccount);
	}
	
	@Test
	@Order(3)	
	public void testDeleteAccount() {
		Account account = accountRepository.findById(accountId).get();
		
		accountRepository.delete(account);
	}
	
	@AfterAll
	public void afterAllTest() {
		customerRepository.deleteById(customerId);
	}
}
