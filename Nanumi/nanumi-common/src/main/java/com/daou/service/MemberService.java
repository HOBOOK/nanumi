package com.daou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daou.entity.Member;
import com.daou.repository.MemberRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Song
 */

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	public List<Member> findAll() {
		List<Member> members = new ArrayList<>();
		memberRepository.findAll().forEach(e -> members.add(e));
		return members;
	}

	public Optional<Member> findByMbrNo(Long mbrNo) {
		Optional<Member> member = memberRepository.findByMbrNo(mbrNo);
		return member;
	}

	public Optional<Member> findById(String id) {
		Optional<Member> member = memberRepository.findById(id);
		return member;
	}

	public void deleteById(Long mbrNo) {
		memberRepository.deleteById(mbrNo);
	}

	public Member save(Member member) {
		memberRepository.save(member);
		return member;
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
