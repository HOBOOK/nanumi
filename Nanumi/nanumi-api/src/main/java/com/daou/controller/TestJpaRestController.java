package com.daou.controller;

import com.daou.entity.Member;
import com.daou.repository.MemberRepository;
import com.daou.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
	@author Song
 */

// TestController

@RestController 
@RequestMapping("memberTest") 
public class TestJpaRestController { 
	// 기본형 
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired 
	MemberService memberService; 
	
	@Autowired
	MemberRepository memberRepository;
	
	// 모든 회원 조회 
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<List<Member>> getAllmembers() {
		List<Member> member = memberService.findAll();
		System.out.println(member);
		return new ResponseEntity<List<Member>>(member, HttpStatus.OK);
	} 
	
	// 회원번호로 한명의 회원 조회 
	@GetMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<Member> getMember(@PathVariable("mbrNo") Long mbrNo) {
		Optional<Member> member = memberService.findByMbrNo(mbrNo);
		return new ResponseEntity<Member>(member.get(), HttpStatus.OK); }
	
	// 회원번호로 회원 삭제 
	@DeleteMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<Void> deleteMember(@PathVariable("mbrNo") Long mbrNo) { 
		memberService.deleteById(mbrNo); 
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); } 
	
	// 회원번호로 회원 수정(mbrNo로 회원을 찾아 Member 객체의 id, name로 수정함) 
//	@PutMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE }) 
//	public ResponseEntity<MemberVo> updateMember(@PathVariable("mbrNo") Long mbrNo, MemberVo member) { 
//		memberService.updateById(mbrNo, member); 
//		return new ResponseEntity<MemberVo>(member, HttpStatus.OK); } 
	
	// 회원 입력 
//	@PostMapping
//	public ResponseEntity<Member> save(Member member) {
//		return new ResponseEntity<Member>(memberService.save(member), HttpStatus.OK);
//	}
	
	// 회원 입력
//	@RequestMapping(value="/saveMember", method = RequestMethod.GET)
//	public ResponseEntity<Member> save(HttpServletRequest req, Member member){
//		return new ResponseEntity<Member>(memberService.save(member), HttpStatus.OK);
//	}
	
	@GetMapping("/search")
	public String search() {
		String result = "";
		for (Member member : memberRepository.findAll()) {
			result += member + "</br>";
		}
		return result;
	}

}
