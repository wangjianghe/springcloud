package com.wjh.web.example.exampleweb.controller;

import com.wjh.web.example.exampleweb.service.LogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogInfoController {
    @Autowired
    LogInfoService logInfoService;
    @PostMapping("/test")
    public void test(){
        logInfoService.testService();
    }
}
