package com.daou.controller;

import com.daou.entity.BandLog;
import com.daou.repository.BandLogRepository;
import com.daou.service.BandLogService;
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

/**
 * 해당 컨드롤러로 이동
 */
@CrossOrigin
@RestController 
@RequestMapping("/api/band/log")
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
		List<BandLog> bandLogs = bandLogService.findAll();
		System.out.println(bandLogs);
		if(bandLogs.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BandLog>>(bandLogs, HttpStatus.OK);
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

}
