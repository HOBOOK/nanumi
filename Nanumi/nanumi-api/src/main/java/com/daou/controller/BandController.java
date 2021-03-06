package com.daou.controller;

import com.daou.common.ErrorCode;
import com.daou.common.ErrorResponse;
import com.daou.common.ValidationCheck;
import com.daou.entity.BandLog;
import com.daou.service.BandLogService;
import com.daou.types.BandLogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daou.entity.Band;
import com.daou.service.BandService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
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
    ValidationCheck validationCheck;

    /**
     * Band_tb 대역
     */

    // 모든 대역 조회
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> getAllbands() {
        try{
            List<Band> band = bandService.findAll();
            if(band.isEmpty()) {
                return new ResponseEntity<Object>(ErrorResponse.of("조회된 대역이 없음", ErrorCode.FAIL_READ_BAND, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Object>(band, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //serialNo로 대역번호 조회
    @GetMapping(value = "/{serialNo}",produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> getBand(@PathVariable("serialNo") String serialNo) {
        try{
            Optional<Band> band = bandService.findBySerialNo(serialNo);
            if(!band.isPresent()) {
                return new ResponseEntity<Object>(ErrorResponse.of("조회된 대역이 없음", ErrorCode.FAIL_READ_BAND, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Object>(band, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 입력받은 category 대역번호 조회
     * @param category
     * 대역번호 페이지에 출력
     * @return
     */
    @GetMapping(value = "/category/{category}",produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> getBandFindCategory(@PathVariable("category") String category) {
        try{
            //입력 값 String category -> enum타입으로 변환하여 조회
            com.daou.types.category category_enum = com.daou.types.category.valueOf(category);
            List<Band> bands = bandService.findByCategory(category_enum);

            if(bands.isEmpty()) {
                return new ResponseEntity<Object>(ErrorResponse.of("조회된 대역이 없음", ErrorCode.FAIL_READ_BAND, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Object>(bands, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Insert
     * 통신사로부터 받은 대역을 등록
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> save(HttpServletRequest req, @RequestBody Band band){
        try{
            if(!validationCheck.validBandRange(band.getStartNo(), band.getEndNo()))
            {
                return new ResponseEntity<Object>(ErrorResponse.of("옳바르지 않은 데이터 포맷", ErrorCode.FAIL_CREATE_BAND, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Object>(bandService.save(band), HttpStatus.OK);
        }catch (Exception e){
            logger.error("대역 테이블 INSERT 과정에서 Rollback 발생");
            return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Update - 대역 범위, 카테고리, 상태 수정 가능
     */
    @PutMapping(value = "/{serialNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> update(@PathVariable("serialNo") String serialNo, @RequestBody Band band) {
        try{
            if(!bandService.findBySerialNo(serialNo).isPresent()){
                return new ResponseEntity<Object>(ErrorResponse.of("존재하지 않는 대역 입력", ErrorCode.FAIL_READ_BAND, HttpStatus.NO_CONTENT), HttpStatus.NO_CONTENT);
            }
            if(!validationCheck.validBandRange(band.getStartNo(), band.getEndNo()))
            {
                return new ResponseEntity<Object>(ErrorResponse.of("옳바르지 않은 데이터 포맷", ErrorCode.FAIL_UPDATE_BAND, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
            }
            bandService.updateByBandNumberRange(serialNo, band);
            return new ResponseEntity<Object>(band, HttpStatus.OK);
        }catch (Exception e){
            logger.error("대역 테이블 UPDATE 과정에서 Rollback 발생");
            return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Delete
     */
    @DeleteMapping(value = "/{serialNo}")
    public ResponseEntity<Object> delete(@PathVariable("serialNo") String serialNo, @RequestBody Band band){
        try{
            bandService.deleteBand(serialNo);
            return new ResponseEntity<Object>(band, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    /**
     * 대역 로그
     */

    // 모든 대역 로그 출력
    @GetMapping(value = "/log", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> getAllbandLogs() {
        try{
            List<BandLog> bandLogs = bandLogService.findAll();

            if(bandLogs.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Object>(bandLogs, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 대역 로그 검색
    @GetMapping(value = "/log/{seqLogNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> getBandLog(@PathVariable("seqLogNo") Long seqLogNo) {
        try{
            Optional<BandLog> bandLog = bandLogService.findBySeqLogNo(seqLogNo);
            return new ResponseEntity<Object>(bandLog.get(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 대역 로그 상태 검색
    @GetMapping(value = "/log/state/{revType}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> getBand(@PathVariable("revType") BandLogType revType) {
        try{
            List<BandLog> bandLog = bandLogService.findByRevType(revType);

            if(bandLog.isEmpty()) {
                return new ResponseEntity(ErrorResponse.of("대역 로그 없음", ErrorCode.FAIL_READ_BAND, HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Object>(bandLog, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Object>(ErrorResponse.of("내부 서버 오류", ErrorCode.GLOBAL,HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 대역의 국번, 지번 조회
    @GetMapping(value = "/local")
    public ResponseEntity<Object> getLocalCodes(){
        try{
            return new ResponseEntity<Object>(bandService.findLocalNumbers(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Object>(ErrorResponse.of("조회된 지번 없음", ErrorCode.FAIL_READ_BAND, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/base/{localNumber}")
    public ResponseEntity<Object> getBaseCodes(@PathVariable("localNumber") String localNumber){
        try{
            return new ResponseEntity<Object>(bandService.findBaseNumbers(localNumber), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Object>(ErrorResponse.of("조회된 국번 없음", ErrorCode.FAIL_READ_BAND, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }
}
