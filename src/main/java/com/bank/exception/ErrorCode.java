package com.bank.exception;

public enum ErrorCode {
	
	
	INVALID_SOURCE_ACC(1, "Invalid source account number"),
	INVALID_TARGET_ACC(2, "Invalid target account number"),
	INVALID_WIRE_VALUE(3, "Invalid wire transfer value"),
	INSUFFICIENT_BALANCE(4, "Insufficient balance");
	 
	private Integer error;
	private String message; 
	
	private ErrorCode(Integer error, String message){
		this.error = error;
		this.message = message;
	}

	public Integer getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}
}
