package com.bank.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bank.model.Account;

/**
 * @author diego.rodrigues
 *
 */
@Repository
@Transactional
public interface AccountRepository extends CrudRepository<Account, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Account findOneByNumber(Long id);
}
