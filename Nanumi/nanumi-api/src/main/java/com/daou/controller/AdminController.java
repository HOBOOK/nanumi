package com.daou.controller;

import com.daou.common.ErrorCode;
import com.daou.common.ErrorResponse;
import com.daou.common.Logger;
import com.daou.common.NanumiUtil;
import com.daou.entity.Account;
import com.daou.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 관리자 권한이 필요한 앤드포인트
 * @author pkh879
 */

@RestController
@RequestMapping(value = "api/admin/")
@PreAuthorize("hasRole('ROLE_ADMIN')") //토큰에 ROLE_ADMIN 권한이 있는 경우 접근 가능한 앤드 포인트
 // ADMIN 권한만 접근 가능 설정
public class AdminController {
    @Autowired private AccountService accountService;
    @Autowired private NanumiUtil nanumiUtil;
    
    // 사용자 계정 추가 앤드 포인트
    @PostMapping(value="account")
    public ResponseEntity<Object> create(@RequestBody Account account){
        if(accountService.findById(account.getId()).isPresent()){
            return new ResponseEntity<Object>(ErrorResponse.of("이미 존재 하는 계정 생성 시도", ErrorCode.FAIL_CREATE_ACCOUNT_EXIST, HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        account.setPwd(encoder.encode(account.getPwd()));
        accountService.save(account);
        return new ResponseEntity<Object>(account, HttpStatus.OK);
    }

    // 모든 사용자 조회 앤드 포인트
    @GetMapping(value = "account")
    public ResponseEntity<Object> read(HttpServletRequest request){
//        API 요청수 증가 코드
//        String requestId = nanumiDecoder.base64DecodeForRequestId(request.getHeader("Authorization"));
//        accountService.saveRequestCount(requestId);
        List<Account> accounts = accountService.findAll();
        if(accounts.isEmpty()) {
            return new ResponseEntity<Object>(ErrorResponse.of("존재 하지 않는 계정 조회", ErrorCode.FAIL_READ_ACCOUNT_NOT_EXIST, HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>(accounts, HttpStatus.OK);
    }

    // 사용자 계정 수정 앤드 포인트
    @PutMapping(value = "account")
    public ResponseEntity<Object> update(@RequestBody Account account){
        if(!accountService.findById(account.getId()).isPresent()){
            return new ResponseEntity<Object>(ErrorResponse.of("존재 하지 않는 계정 수정", ErrorCode.FAIL_UPDATE_ACCOUNT_NOT_EXIST, HttpStatus.NO_CONTENT),HttpStatus.NO_CONTENT);
        }
        accountService.save(account);
        return new ResponseEntity<Object>(account, HttpStatus.OK);
    }
    
    // 사용자 계정 삭제 앤드 포인트
    @DeleteMapping(value="account")
    public ResponseEntity<Object> delete(@RequestBody Account account){
        if(!accountService.findById(account.getId()).isPresent()){
            return new ResponseEntity<Object>(ErrorResponse.of("존재 하지 않는 계정 삭제", ErrorCode.FAIL_DELETE_ACCOUNT_NOT_EXIST, HttpStatus.NO_CONTENT),HttpStatus.NO_CONTENT);
        }
        accountService.deleteById(account.getId());
        return new ResponseEntity<Object>(account, HttpStatus.OK);
    }

}
