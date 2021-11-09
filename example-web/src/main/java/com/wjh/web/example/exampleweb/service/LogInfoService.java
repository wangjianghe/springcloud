package com.wjh.web.example.exampleweb.service;

import com.wjh.example.api.feign.RemoteLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogInfoService {
    @Autowired
    RemoteLogService remoteLogService;
    public void testService(){
        remoteLogService.testLog();
    }
}
