package com.daou.controller;

import com.daou.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    MemberService memberService;
    @GetMapping("/")
    public String getUser(){
        if(memberService.findByMbrNo((long)1).isPresent()){
            return memberService.findByMbrNo((long)1).get().getName();
        }
        return "null";
    }

    @GetMapping("/api/auth/login")
    public String login(){
        return "OK";
    }
}
