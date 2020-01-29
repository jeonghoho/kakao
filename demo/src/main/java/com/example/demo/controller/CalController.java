package com.example.demo.controller;

import com.example.demo.dto.CalValue;
import com.example.demo.service.CalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalController {
    private CalService calService;

    CalController(CalService calService){
        this.calService = calService;
    }
    @GetMapping("/plus")
    public CalValue plus(CalValue calValue){
        calValue.setResultValue(calService.plus(calValue.getValue1(),calValue.getValue2()));
        return calValue;
    }
}
