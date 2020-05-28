package com.daou.controller;

import com.daou.service.BandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/band")
public class BandController {
    @Autowired
    private BandService bandService;

    @GetMapping("/")
    public String getBand(){
        return bandService.findAll().toString();
    }
}
