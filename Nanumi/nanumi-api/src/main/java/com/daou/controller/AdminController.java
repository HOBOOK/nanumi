package com.daou.controller;

import com.daou.common.Logger;
import com.daou.entity.Account;
import com.daou.entity.Band;
import com.daou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    
    // 사용자 계정 추가 앤드 포인트
    @PostMapping(value="account")
    public ResponseEntity<Object> create(@RequestBody Account account){
        if(accountService.findById(account.getId()).isPresent()){
            return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        account.setPwd(encoder.encode(account.getPwd()));
        accountService.save(account);
        return new ResponseEntity<Object>(account, HttpStatus.OK);
    }

    // 모든 사용자 조회 앤드 포인트
    @GetMapping(value = "account")
    public ResponseEntity<List<Account>> read(){
        List<Account> accounts = accountService.findAll();
        if(accounts.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
    }

    // 사용자 계정 수정 앤드 포인트
    @PutMapping(value = "account")
    public ResponseEntity<Object> update(@RequestBody Account account){
        if(!accountService.findById(account.getId()).isPresent()){
            return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
        }
        accountService.save(account);
        return new ResponseEntity<Object>(account, HttpStatus.OK);
    }
    
    // 사용자 계정 삭제 앤드 포인트
    @DeleteMapping(value="account")
    public ResponseEntity<Object> delete(@RequestBody Account account){
        Logger.write(account.toString());
        if(!accountService.findById(account.getId()).isPresent()){
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
        accountService.deleteById(account.getId());
        return new ResponseEntity<Object>(account, HttpStatus.OK);
    }

}
