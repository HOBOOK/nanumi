package com.daou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daou.entity.Account;
import com.daou.repository.AccountRepository;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Song
 * @author pkh879
 */

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Transactional
	public List<Account> findAll() {
		List<Account> accounts = new ArrayList<>();
		accountRepository.findAll().forEach(e -> accounts.add(e));
		return accounts;
	}

	@Transactional
	public Optional<Account> findById(String id) {
		Optional<Account> member = accountRepository.findById(id);
		return member;
	}

	@Transactional
	public void deleteById(String id) {
		accountRepository.deleteById(id);
	}

	@Transactional
	public Account save(Account account) {
		accountRepository.save(account);
		return account;
	}

	// api 요청수 증가 함수
	@Transactional
	public void saveRequestCount(String id){
		if(accountRepository.findById(id).isPresent()){
			Account account = accountRepository.findById(id).get();
			account.setRequestCnt(account.getRequestCnt()+1);
			accountRepository.save(account);
		}
	}

}
