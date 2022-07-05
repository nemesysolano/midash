package com.midash.bank.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import com.midash.bank.BaseServiceTest;
import com.midash.bank.model.Customer;

public class CustomerRepositoryTest extends BaseServiceTest{
	static Logger logger = Logger.getLogger(CustomerRepositoryTest.class);
	
	@Autowired
	CustomerRepository customerRepository;
	
	private static String customerId = RandomStringUtils.random(UUID_LENGTH, true, true);
	
	@Test
	@Order(1)
	public void testSave () throws JsonProcessingException {		
		Faker faker = new Faker();
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
	@Order(2)
	public void testDelete() {
		Optional<Customer> response = customerRepository.findById(customerId);
		assertTrue(response.isPresent());
		customerRepository.delete(response.get());
		response = customerRepository.findById(customerId);
		assertTrue(!response.isPresent());
	}
	
}
