package com.midash.bank.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * Bank account. So far there is not distintion between savings and check account.
 * @author rsolano
 *
 */
@Jacksonized @Builder(toBuilder=true)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@ToString
@Entity
@Table(name = "account")
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE, force = true)
public class Account implements Serializable {
	private static final long serialVersionUID = 5754385309595300453L;

	/**
	 * UUID v4 string that uniquely identifies the customer.
	 */
	@Id
	@Column(name="account_id", nullable = false)	
	public final String id;
	
	/**
	 * Non negative number.
	 */
	@Column(name="balance", nullable = false)
	public final double balance;
	
	/**
	 * Optional description
	 */
	@Column(name="description", nullable = true)
	public final String name;
	
	/**
	 * Account holder.
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name="customer_id")	
	public final Customer customer;
	
}
