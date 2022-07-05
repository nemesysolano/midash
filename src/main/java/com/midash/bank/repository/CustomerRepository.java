package com.midash.bank.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.midash.bank.model.Customer;

/**
 * Performs CRUD operations on customer table.
 * @author rsolano
 *
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {
	Customer findByPhoneNumber(String phoneNumber);
}
