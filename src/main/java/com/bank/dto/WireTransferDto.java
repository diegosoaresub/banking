package com.bank.dto;

/**
 * @author diego.rodrigues
 *
 */
public class WireTransferDto {

    private Long sourceAccountNumber;
    
    private Long targetAccountNumber;
    
    private Double value;

    public WireTransferDto(){}
    
	public WireTransferDto(Long sourceAccountNumber, Long targetAccountNumber, Double value) {
		this.sourceAccountNumber = sourceAccountNumber;
		this.targetAccountNumber = targetAccountNumber;
		this.value = value;
	}

	public Long getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(Long sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}

	public Long getTargetAccountNumber() {
		return targetAccountNumber;
	}

	public void setTargetAccountNumber(Long targetAccountNumber) {
		this.targetAccountNumber = targetAccountNumber;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
