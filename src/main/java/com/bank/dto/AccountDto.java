package com.bank.dto;

/**
 * @author diego.rodrigues
 *
 */
public class AccountDto {

    private Long number;
    
    private String name;
    
    private Double balance;

	public AccountDto(Long number, String name, Double balance) {
		this.number = number;
		this.name = name;
		this.balance = balance;
	}

	public AccountDto(String name, Double balance) {
		this.name = name;
		this.balance = balance;
	}

	
	public AccountDto() {}

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
    
}
