package com.midash.bank.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * A person (not juridical) that holds at least one account in the bank.
 * JPA + Jackson + Immutability + no accessors
 * @author rsolano
 *
 */
@Jacksonized @Builder(toBuilder=true)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@ToString
@Entity
@Table(name = "customer")
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE, force = true)
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9151334639991047137L;	
	
	/**
	 * UUID v4 string that uniquely identifies the customer.
	 */
	@Id
	@Column(name="customer_id", nullable = false)
	public final String id;
	
	/**
	 * First name (not null)
	 */
	@Column(name="first_name", nullable = false)
	public final String firstName;
	
	/**
	 * Last name (not null)
	 */	
	@Column(name="last_name", nullable = false)
	public final String lastName;
	
	/**
	 * Phone number including country code (not null)
	 */
	@Column(name="phone_number", nullable = false)
	public final String phoneNumber;
	
}
