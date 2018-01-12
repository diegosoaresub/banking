package com.bank.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * @author diego.rodrigues
 *
 */
@Entity(name="account")
public class Account {

	@Id
	@GeneratedValue
	private Long number;

	private String name;
	
	private Double balance;

	public Account(){}

	public Account(Long number, String name, Double balance) {
		this.number = number;
		this.name = name;
		this.balance = balance;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [number=" + number + ", name=" + name + ", balance=" + balance + "]";
	}
}
