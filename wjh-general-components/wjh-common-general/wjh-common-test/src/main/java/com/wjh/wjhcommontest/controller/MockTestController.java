package com.wjh.wjhcommontest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MockTestController {
    @RequestMapping("/testPublicMock")
    public String testPublicMock(String s){
        log.info("test string {}",s);
        return "success";
    }
}
