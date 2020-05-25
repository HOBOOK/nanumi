package com.daou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class NanumiAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(NanumiAPIApplication.class);
    }
}
