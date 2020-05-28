package com.daou.controller;

import com.daou.authentication.auth.ajax.LoginRequest;
import com.daou.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author pkh879
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/api")
public class AuthController {

    @Autowired
    MemberService memberService;

    @GetMapping("/user")
    public String getUser(){
        if(memberService.findByMbrNo((long)1).isPresent()){
            return memberService.findByMbrNo((long)1).get().getName();
        }
        return "null";
    }

    // 인증 URL SWAGGER API 문서 제공용 임시 메서드
    @PostMapping(value = "/auth/login")
    public void createToken (@RequestBody LoginRequest loginRequest){

    }
}
