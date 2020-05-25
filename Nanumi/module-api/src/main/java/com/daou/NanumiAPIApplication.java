package com.daou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableConfigurationProperties
public class NanumiAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(NanumiAPIApplication.class);
    }
}
