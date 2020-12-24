package com.wjh.example.admin.controller;

import com.wjh.example.admin.service.ComponentService;
import com.wjh.example.admin.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "日志接口")
@RequestMapping("/logs")
public class LogController {
    @Autowired
    LogService logService;
    @Autowired
    ComponentService componentService;

    @ApiOperation(value = "invoke", notes = "invoke测试")
    @GetMapping("/testLog")
    public String testLog() {
        logService.testLog();
        componentService.testComponentLog();
        return "success";
    }
}
