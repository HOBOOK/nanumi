package com.daou.controller;

import com.daou.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    MemberService memberService;
    @GetMapping("/")
    public String getUser(){
        if(memberService.findByMbrNo((long)1).isPresent()){
            return memberService.findByMbrNo((long)1).get().getName();
        }
        return "nul";
    }
}
