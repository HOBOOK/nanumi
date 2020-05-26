package com.daou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @RequestMapping("/api/user")
    public String user(){
        return "Hello api user!";
    }

    @RequestMapping("/hello")
    public String everyone(){
        return "Hello everyone!";
    }
}
