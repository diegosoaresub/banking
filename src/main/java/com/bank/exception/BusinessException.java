package com.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author diegosoaresub
 *
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class BusinessException extends Exception{

	private ErrorCode error;
	
	private static final long serialVersionUID = 1L;

	public BusinessException(ErrorCode error){
		super(error.getMessage());
		this.error = error;
	}
	
	public BusinessException(){}

	public ErrorCode getError() {
		return error;
	}
}
