package com.wjh.example.api.feign.factory;

import com.wjh.example.api.feign.RemoteLogService;
import com.wjh.example.api.feign.fallback.RemoteLogServiceImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteLogFallBackFactory implements FallbackFactory<RemoteLogService> {
    @Override
    public RemoteLogService create(Throwable cause) {
        RemoteLogServiceImpl impl=new RemoteLogServiceImpl();
        impl.setCause(cause);
        return impl;
    }
}
