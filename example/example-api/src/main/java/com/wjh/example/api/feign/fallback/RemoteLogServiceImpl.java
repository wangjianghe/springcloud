package com.wjh.example.api.feign.fallback;

import com.wjh.example.api.feign.RemoteLogService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RemoteLogServiceImpl implements RemoteLogService {
    @Setter
    private Throwable cause;

    @Override
    public void testLog() {
        cause.printStackTrace();
        log.error("日志显示api失败",cause);
    }
}
