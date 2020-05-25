package com.daou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daou.entity.MemberVo;
import com.daou.repository.MemberRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	public List<MemberVo> findAll() {
		List<MemberVo> members = new ArrayList<>();
		memberRepository.findAll().forEach(e -> members.add(e));
		
//		System.out.println(members);

		return members;
	}

	public Optional<MemberVo> findById(Long mbrNo) {
		Optional<MemberVo> member = memberRepository.findById(mbrNo);
//		System.out.println(member);
		return member;
	}

	public void deleteById(Long mbrNo) {
		memberRepository.deleteById(mbrNo);
	}

	public MemberVo save(MemberVo member) {
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
