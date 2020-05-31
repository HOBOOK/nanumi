package com.daou.controller;

import com.daou.entity.BandLog;
import com.daou.repository.BandLogRepository;
import com.daou.service.BandLogService;
import com.daou.types.category;
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
@RequestMapping("api/band")
public class BandController {
    // 기본형
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BandService bandService;
    @Autowired
    BandLogService bandLogService;

    @Autowired
    BandRepository bandRepository;
    @Autowired
    BandLogRepository bandLogRepository;


    /**
     * Band_tb 대역
     */

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

    //serialNo로 대역번호 조회
    @GetMapping(value = "/{serialNo}",produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Band> getBand(@PathVariable("serialNo") String serialNo) {
        Optional<Band> band = bandService.findBySerialNo(serialNo);

        return new ResponseEntity<Band>(band.get(), HttpStatus.OK);
    }

    /**
     * 입력받은 category 대역번호 조회
     * @param category
     * 대역번호 페이지에 출력
     * @return
     */
    @GetMapping(value = "/category/{category}",produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Band>> getBandFindCategory(@PathVariable("category") String category) {

        //입력 값 String category -> enum타입으로 변환하여 조회
        com.daou.types.category category_enum = com.daou.types.category.valueOf(category);
        List<Band> band = bandService.findByCategory(category_enum);

        return new ResponseEntity<List<Band>>(band, HttpStatus.OK);
    }




    /**
     * 대역 로그
     */

    // 모든 대역 로그 출력
    @GetMapping(value = "/log", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<BandLog>> getAllbandLogs() {
        List<BandLog> bandLogs = bandLogService.findAll();

        if(bandLogs.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<BandLog>>(bandLogs, HttpStatus.OK);
    }

    // 대역 로그 검색
    @GetMapping(value = "/log/{seqLogNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<BandLog> getBandLog(@PathVariable("seqLogNo") Long seqLogNo) {
        Optional<BandLog> bandLog = bandLogService.findBySeqLogNo(seqLogNo);
        return new ResponseEntity<BandLog>(bandLog.get(), HttpStatus.OK);
    }

    // 대역 로그 상태 검색
    @GetMapping(value = "/log/state/{revType}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<BandLog>> getBand(@PathVariable("revType") byte revType) {
        List<BandLog> bandLog = bandLogService.findByRevType(revType);

        if(bandLog.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<BandLog>>(bandLog, HttpStatus.OK);
    }

}
