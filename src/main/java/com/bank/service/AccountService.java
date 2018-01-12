package com.bank.service;

import com.bank.dto.WireTransferDto;
import com.bank.exception.BusinessException;
import com.bank.model.Account;

public interface AccountService {

	Account findByNumber(Long number);

	Account save(Account acc);
	
	void wireTransfer(WireTransferDto wire) throws BusinessException;
	
}
