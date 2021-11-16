package com.wjh.common.logs.version.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 追踪配置
 */
@Data
public class TracingConfig {
    /**
     * 是否开启日志
     */
    private boolean traceIdLog = false;
    /**
     *controller层打印
     */
    private BasicConfig controller=new BasicConfig(true,true,true,true);
    /**
     * service层打印
     */
    private BasicConfig service=new BasicConfig(false,true,true,true);
    /**
     * feign调用打印
     */
    private BasicConfig feign=new BasicConfig(true,true,true,true);
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BasicConfig{
        private boolean enable;
        private boolean enablePreLogging;
        private boolean enablePostLogging;
        private boolean enableErrorLogging;
    }
}
