package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bank.dto.WireTransferDto;
import com.bank.exception.BusinessException;
import com.bank.exception.ErrorCode;
import com.bank.model.Account;
import com.bank.repository.AccountRepository;

public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository repo;

	@Override
	public Account findByNumber(Long number) {
		return repo.findOne(number);
	}

    @Override
    @Transactional
    public Account save(Account acc) {
        return repo.save(acc);
    }

	@Override
	@Transactional
	public void wireTransfer(WireTransferDto wire) throws BusinessException {
		
		//Find the source account with for update restriction
		Account source = repo.findOneByNumber(wire.getSourceAccountNumber());
		if(source == null)
			throw new BusinessException(ErrorCode.INVALID_SOURCE_ACC);
		
		//Find the target account with for update restriction
		Account target = repo.findOneByNumber(wire.getTargetAccountNumber());
		if(target == null)
			throw new BusinessException(ErrorCode.INVALID_TARGET_ACC);
		
		//Check if the value is valid
		if(wire.getValue() == null || wire.getValue() <= 0)
			throw new BusinessException(ErrorCode.INVALID_WIRE_VALUE);
		
		//Check if the source account balance
		if(source.getBalance() < wire.getValue())
			throw new BusinessException(ErrorCode.INSUFFICIENT_BALANCE);
		
		source.setBalance(source.getBalance() - wire.getValue());
		target.setBalance(target.getBalance() + wire.getValue());
		
		repo.save(source);
		repo.save(target);
	}
	
}
