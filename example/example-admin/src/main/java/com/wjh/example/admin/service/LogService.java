package com.wjh.example.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogService {
    public void testLog(){
        log.info("test log");
        log.error("test log error");
    }
}
