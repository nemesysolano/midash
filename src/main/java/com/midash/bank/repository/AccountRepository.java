package com.midash.bank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.midash.bank.model.Account;

/**
 * Performs CRUD operations on account table.
 * @author rsolano
 *
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, String>{

}
