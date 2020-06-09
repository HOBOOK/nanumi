package com.daou.controller;

import com.daou.common.ErrorCode;
import com.daou.common.ErrorResponse;
import com.daou.entity.BandReceive;
import com.daou.mapping.bandMapping;
import com.daou.repository.BandReceiveRepository;
import com.daou.service.BandReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
	@author Song
 	@author jsw
 */

@CrossOrigin //웹 페이지의 제한된 자원을 외부 도메인에서 접근을 허용
@RestController 
@RequestMapping("/api/receptions")
public class BandReceiveController {
	// 기본형 
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	BandReceiveService bandReceiveService;
	
	// 모든 대역 로그 출력
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<List<BandReceive>> getAllbands() {
		try{
			List<BandReceive> bandReceives = bandReceiveService.findAll();
			System.out.println(bandReceives);
			if(bandReceives.isEmpty()) {
				return new ResponseEntity(ErrorResponse.of("조회된 대역 정보 없음", ErrorCode.FAIL_READ_RECEIVING_NUMBER, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<BandReceive>>(bandReceives, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping(value = "/{serialNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<BandReceive>> getBandBySerialNo(@PathVariable("serialNo") String serialNo) {
		try{
			List<BandReceive> bandReceive = bandReceiveService.findBySerialNo(serialNo);
			if(bandReceive.isEmpty()) {
				return new ResponseEntity(ErrorResponse.of("조회된 대역 정보 없음", ErrorCode.FAIL_READ_RECEIVING_NUMBER, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<BandReceive>>(bandReceive, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/receive/{receiveNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<BandReceive>> getBand(@PathVariable("receiveNo") String receiveNo) {
		try{
			List<BandReceive> bandReceive = bandReceiveService.findByReceiveNo(receiveNo);
			if(bandReceive.isEmpty()) {
				return new ResponseEntity(ErrorResponse.of("조회된 수신 번호 없음", ErrorCode.FAIL_READ_RECEIVING_NUMBER, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<List<BandReceive>>(bandReceive, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/receive/{seqNo}")
	public ResponseEntity<Object> update(@RequestBody BandReceive bandReceive){
		try{
			if(!bandReceiveService.findBySeqNo(bandReceive.getSeqNo()).isPresent()){
				return new ResponseEntity(ErrorResponse.of("조회된 수신 번호 없음", ErrorCode.FAIL_UPDATE_RECEIVING_NUMBER, HttpStatus.NO_CONTENT), HttpStatus.NO_CONTENT);
			}
			bandReceive.setUpdateDate(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
			bandReceiveService.save(bandReceive);
			return new ResponseEntity<Object>(bandReceive, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
