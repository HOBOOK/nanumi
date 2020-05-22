package com.daou.nanumi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class NanumiWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(NanumiWebApplication.class);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String test(){
        return "Hello world";
    }
}
