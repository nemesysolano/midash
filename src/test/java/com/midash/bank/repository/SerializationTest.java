package com.midash.bank.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.midash.bank.BaseServiceTest;
import com.midash.bank.model.Customer;

public class SerializationTest extends BaseServiceTest{
	static Logger logger = LoggerFactory.getLogger(SerializationTest.class);
	Faker faker = new Faker();
	
	@Test
	@Order(1)
	public void testSerialization() throws JsonProcessingException {
		String customerId = RandomStringUtils.random(UUID_LENGTH, true, true);
		ObjectMapper objectMapper = new ObjectMapper();
		Customer customer = Customer.builder()
				.id(customerId)
				.firstName(faker.address().firstName())
				.lastName(faker.address().lastName())
				.phoneNumber(faker.phoneNumber().cellPhone())
				.build();		
		String customerJSON = objectMapper.writeValueAsString(customer);
		Customer parsedCustomer = objectMapper.readValue(customerJSON, Customer.class);
		
		assertEquals(parsedCustomer.id, customer.id);
		logger.debug("JSON={}", customerJSON);
		
	}
}
