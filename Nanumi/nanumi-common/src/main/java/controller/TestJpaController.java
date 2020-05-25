//package com.daou.controller;
//
//import com.daou.entity.BandVo;
//import com.daou.service.BandService;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("bandTest")
//public class TestJpaController {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//	
//	@Autowired
//	BandService bandService;
//
//	// 입력
//	@PostMapping
//	public ResponseEntity<BandVo> save(BandVo band) {
//		return new ResponseEntity<BandVo>(bandService.save(band), HttpStatus.OK);
//	}
//
//}
