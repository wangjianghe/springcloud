package com.wjh.example.api.feign;


import com.wjh.example.api.feign.factory.RemoteLogFallBackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = "remoteLogService",value = "example-admin",path = "/logs",fallbackFactory = RemoteLogFallBackFactory.class)
public interface RemoteLogService {

    @ApiOperation(value = "动态分页查询", notes = "动态分页查询")
    @GetMapping("/testLog")
    void testLog();
}
