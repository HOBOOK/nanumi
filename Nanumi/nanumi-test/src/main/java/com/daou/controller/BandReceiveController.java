package com.daou.controller;

import com.daou.entity.BandReceive;
import com.daou.repository.BandReceiveRepository;
import com.daou.service.BandReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController 
@RequestMapping("bandreceive")

public class BandReceiveController {
	// 기본형 
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	BandReceiveService bandReceiveService;
	
	@Autowired
	BandReceiveRepository bandReceiveRepository;
	
	// 모든 대역 로그 출력
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<List<BandReceive>> getAllbands() {
		List<BandReceive> bandReceives = bandReceiveService.findAll();
		System.out.println(bandReceives);
		if(bandReceives.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BandReceive>>(bandReceives, HttpStatus.OK);
	} 
	
	// seq_log_no 대역 로그 검색 출력
	@GetMapping(value = "/{seqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BandReceive> getBand(@PathVariable("seqNo") Long seqNo) {
		Optional<BandReceive> bandReceive = bandReceiveService.findBySeqNo(seqNo);
		return new ResponseEntity<BandReceive>(bandReceive.get(), HttpStatus.OK);
	}
	
	// seq_log_no 대역 로그 상태 검색
	@GetMapping(value = "/receive/{receiveNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<BandReceive>> getBand(@PathVariable("receiveNo") String receiveNo) {
		List<BandReceive> bandReceive = bandReceiveService.findByReceiveNo(receiveNo);
		
		if(bandReceive.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BandReceive>>(bandReceive, HttpStatus.OK);
	}

}
