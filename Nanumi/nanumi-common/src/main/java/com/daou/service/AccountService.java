package com.daou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daou.entity.Account;
import com.daou.repository.AccountRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Song
 */

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;

	public List<Account> findAll() {
		List<Account> accounts = new ArrayList<>();
		accountRepository.findAll().forEach(e -> accounts.add(e));
		return accounts;
	}

	public Optional<Account> findById(String id) {
		Optional<Account> member = accountRepository.findById(id);
		return member;
	}

	public void deleteById(String id) {
		accountRepository.deleteById(id);
	}

	public Account save(Account account) {
		accountRepository.save(account);
		return account;
	}

//	public void updateById(Long mbrNo, MemberVo member) {
//		Optional<MemberVo> e = memberRepository.findById(mbrNo);
//		if (e.isPresent()) {
//			e.get().setMbrNo(member.());
//			e.get().setId(member.getId());
//			e.get().setName(member.getName());
//			memberRepository.save(member);
//		}
//	}
}
