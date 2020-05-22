package com.daou.controller;

import com.daou.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    //Common 모듈의 서비스 사용
    @Autowired
    TestService testService;

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public String test(){
        return testService.testText();
    }
}
