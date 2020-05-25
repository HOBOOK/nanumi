package com.daou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
    @Autowired
//    TestService testService;

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public String test(){
//        return testService.testText();
    	return "";
    }
}
