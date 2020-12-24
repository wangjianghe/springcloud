package com.wjh.example.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ComponentService {

    public void testComponentLog(){
      log.info("component service");
    }
}

