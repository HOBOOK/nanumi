package com.daou.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daou.entity.Band;
import com.daou.repository.BandRepository;
import com.daou.service.BandService;

import java.util.List;
import java.util.Optional;

/*
	@author Song
 */

@CrossOrigin
@RestController 
@RequestMapping("band") 
public class BandController { 
	// 기본형 
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired 
	BandService bandService; 
	
	@Autowired
	BandRepository bandRepository;
	
	// 모든 대역 조회
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<List<Band>> getAllbands() {
		List<Band> band = bandService.findAll();
		System.out.println(band);
		if(band.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Band>>(band, HttpStatus.OK);
	} 

	@GetMapping(value = "/{seqNo}", produces = { MediaType.APPLICATION_JSON_VALUE }) 
	public ResponseEntity<Band> getBand(@PathVariable("seqNo") Long seqNo) {
		Optional<Band> band = bandService.findBySeqNo(seqNo);
		return new ResponseEntity<Band>(band.get(), HttpStatus.OK);
	}

}
