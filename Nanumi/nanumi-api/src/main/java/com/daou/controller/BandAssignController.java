package com.daou.controller;

import com.daou.common.ErrorCode;
import com.daou.common.ErrorResponse;
import com.daou.common.ValidationCheck;
import com.daou.entity.Band;
import com.daou.entity.BandAssign;
import com.daou.mapping.AccountMapping;
import com.daou.repository.BandAssignRepository;
import com.daou.service.BandAssignService;
import com.daou.service.BandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;


/*
	@author Song
 */

@CrossOrigin
@RestController
@RequestMapping("api/assignments")
public class BandAssignController {
	// 기본형 
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	BandAssignService bandAssignService;

	@Autowired
	BandService bandService;

	@Autowired
	BandAssignRepository bandAssignRepository;

	@Autowired
	ValidationCheck validationCheck;


	// 모든 할당 대역 출력
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> findInfo() {
		try{
			List<AccountMapping> bandAssigns = bandAssignService.findAllBy();
			if(bandAssigns.isEmpty()) {
				return new ResponseEntity<Object>(ErrorResponse.of("조회된 대역이 없음", ErrorCode.FAIL_READ_ASSIGN_BAND, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Object>(bandAssigns, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// serialNo 할당 대역 검색
	@GetMapping(value = "/{serialNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> findBySerialNo(@PathVariable("serialNo") String serialNo) {
		try{
			List<BandAssign> bandAssigns = bandAssignService.findBySerialNo(serialNo);
			if(bandAssigns.isEmpty()) {
				return new ResponseEntity<Object>(ErrorResponse.of("조회된 대역이 없음", ErrorCode.FAIL_READ_ASSIGN_BAND, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Object>(bandAssigns, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	// service code 검색
	@GetMapping(value = "/service/{serviceCode}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> getBand(@PathVariable("serviceCode") String serviceCode) {
		try{
			List<BandAssign> bandAssign = bandAssignService.findBySvcId(serviceCode);
			if(bandAssign.isEmpty()) {
				return new ResponseEntity<Object>(ErrorResponse.of("조회된 대역이 없음", ErrorCode.FAIL_READ_ASSIGN_BAND, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Object>(bandAssign, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	/**
	 * Insert
	 * 사용자에게 할당  serialNo, startNo, endNo, svcId(Account에 id사용자 아이디)
	 * (validationCheck)
	 * 1. Band_tb의 Start/EndNo 범위 확인 처리 -> 2.입력값 유효성 검사
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> save(HttpServletRequest req, @RequestBody BandAssign bandAssign) {
		try{
			Optional<Band> band = bandService.findBySerialNo(bandAssign.getSerialNo());
			if (band.isPresent()){
				if(!validationCheck.validBandAssignRange(band, bandAssign))
					return new ResponseEntity<Object>(ErrorResponse.of("옳바르지 않은 데이터 포맷", ErrorCode.FAIL_CREATE_ASSIGN_BAND, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
			}else{
				return new ResponseEntity<Object>(ErrorResponse.of("존재하지 않는 대역 번호", ErrorCode.FAIL_UPDATE_ASSIGN_BAND, HttpStatus.NO_CONTENT), HttpStatus.NO_CONTENT);
			}
			bandAssignService.insertByBandNumberRange(bandAssign);
			return new ResponseEntity<Object>(bandAssignRepository.save(bandAssign), HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	/**
	 * Update - 대역 범위 수정 (SEQ_NO로 검색 START_NO, END_NO 수정)
	 * (validationCheck)
	 *  1. Band_tb의 Start/EndNo 범위 확인 처리 -> 2. 입력값 유효성 검사
	 */
	@RequestMapping(method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> update(@RequestBody BandAssign bandAssign) {
		try{
			Optional<Band> band = bandService.findBySerialNo(bandAssign.getSerialNo());
			if (band.isPresent()){
				if(!validationCheck.validBandAssignRange(band, bandAssign))
					return new ResponseEntity<Object>(ErrorResponse.of("옳바르지 않은 데이터 포맷", ErrorCode.FAIL_CREATE_ASSIGN_BAND, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
			}else{
				return new ResponseEntity<Object>(ErrorResponse.of("존재하지 않는 대역 번호", ErrorCode.FAIL_UPDATE_ASSIGN_BAND, HttpStatus.NO_CONTENT), HttpStatus.NO_CONTENT);
			}

			bandAssignService.updateByBandNumberRange(bandAssign);
			return new ResponseEntity<Object>(bandAssign, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	/**
	 * Delete
	 */
	@DeleteMapping(value = "/{seqNo}")
	public ResponseEntity<Object> delete(@PathVariable("seqNo") Long seqNo, BandAssign bandAssign){
		try{
			bandAssignService.deleteBySeqNo(seqNo);
			return new ResponseEntity<Object>(bandAssign, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
