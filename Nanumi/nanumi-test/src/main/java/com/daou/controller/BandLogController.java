package com.daou.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daou.entity.BandLog;
import com.daou.repository.BandLogRepository;
import com.daou.service.BandLogService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController 
@RequestMapping("bandLog") 
public class BandLogController { 
	// 기본형 
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired 
	BandLogService bandLogService; 
	
	@Autowired
	BandLogRepository bandRepository;
	
	// 모든 대역 로그 출력
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<List<BandLog>> getAllbands() {
		List<BandLog> bandLog = bandLogService.findAll();
		System.out.println(bandLog);
		if(bandLog.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BandLog>>(bandLog, HttpStatus.OK);
	} 
	
	// seq_log_no 대역 로그 검색 출력
	@GetMapping(value = "/{seqLogNo}", produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<BandLog> getBand(@PathVariable("seqLogNo") Long seqLogNo) {
		Optional<BandLog> bandLog = bandLogService.findBySeqLogNo(seqLogNo);
		return new ResponseEntity<BandLog>(bandLog.get(), HttpStatus.OK); 
	}
	
	// seq_log_no 대역 로그 상태 검색
	@GetMapping(value = "/type/{revType}", produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<List<BandLog>> getBand(@PathVariable("revType") byte revType) {
		List<BandLog> bandLog = bandLogService.findByRevType(revType);
		
		if(bandLog.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BandLog>>(bandLog, HttpStatus.OK);
	}
	
	// 회원번호로 회원 삭제 
//	@DeleteMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE }) 
//	public ResponseEntity<Void> deleteMember(@PathVariable("mbrNo") Long mbrNo) { 
//		memberService.deleteById(mbrNo); 
//		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); } 
	
	// 회원번호로 회원 수정(mbrNo로 회원을 찾아 Member 객체의 id, name로 수정함) 
//	@PutMapping(value = "/{mbrNo}", produces = { MediaType.APPLICATION_JSON_VALUE }) 
//	public ResponseEntity<MemberVo> updateMember(@PathVariable("mbrNo") Long mbrNo, MemberVo member) { 
//		memberService.updateById(mbrNo, member); 
//		return new ResponseEntity<MemberVo>(member, HttpStatus.OK); } 
	
	// 회원 입력 
//	@PostMapping 
//	public ResponseEntity<Band> save(Band band) {
//		return new ResponseEntity<Band>(bandService.save(band), HttpStatus.OK);
//	} 
	
//	// 회원 입력
//	@RequestMapping(value="/saveMember", method = RequestMethod.GET) 
//	public ResponseEntity<Member> save(HttpServletRequest req, Member member){
//		return new ResponseEntity<Member>(memberService.save(member), HttpStatus.OK);
//	}
	
//	@GetMapping("/search")
//	public String search() {
//		String result="";
//		for(Member member : memberRepository.findAll() ) {
//			result += member +"</br>";
//		}
//		return result;
//	}
}
