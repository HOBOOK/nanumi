package com.daou.controller;

import com.daou.entity.BandAssign;
import com.daou.mapping.AssignMapping;
import com.daou.repository.BandAssignRepository;
import com.daou.service.BandAssignService;
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
	BandAssignRepository bandAssignRepository;
	
	// 모든 할당 대역 출력
//	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<List<BandAssign>> getAllbands() {
//		List<BandAssign> bandAssigns = bandAssignService.findAll();
//		System.out.println(bandAssigns);
//		if(bandAssigns.isEmpty()) {
//			return new ResponseEntity(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<List<BandAssign>>(bandAssigns, HttpStatus.OK);
//	}
	
	// seqNo 할당 대역 검색
	@GetMapping(value = "/{seqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BandAssign> getBand(@PathVariable("seqNo") Long seqNo) {
		Optional<BandAssign> bandAssign = bandAssignService.findBySeqNo(seqNo);
		return new ResponseEntity<BandAssign>(bandAssign.get(), HttpStatus.OK);
	}

	// 모든 대역 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<AssignMapping>> findInfo() {
		List<AssignMapping> bandAssigns = bandAssignService.findAllBy();
		if(bandAssigns.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<AssignMapping>>(bandAssigns, HttpStatus.OK);
	}



	// service code 검색
	@GetMapping(value = "/service/{serviceCode}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<BandAssign>> getBand(@PathVariable("serviceCode") String serviceCode) {
		List<BandAssign> bandAssign = bandAssignService.findBySvcId(serviceCode);
		
		if(bandAssign.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BandAssign>>(bandAssign, HttpStatus.OK);
	}


	/**
	 * Insert
	 * 사용자에게 할당
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<BandAssign> save(HttpServletRequest req, BandAssign bandAssign){
		return new ResponseEntity<BandAssign>(bandAssignRepository.save(bandAssign), HttpStatus.OK);
	}


	/**
	 * Update - 대역 범위 수정 (SEQ_NO로 검색 START_NO, END_NO 수정)
	 */
	@PutMapping(value = "/{seqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BandAssign> updateAssignBand(@PathVariable("seqNo") Long seqNo, BandAssign bandAssign) {
		bandAssignService.updateByBandNumberRange(seqNo, bandAssign);
		return new ResponseEntity<BandAssign>(bandAssign, HttpStatus.OK);
	}

}
