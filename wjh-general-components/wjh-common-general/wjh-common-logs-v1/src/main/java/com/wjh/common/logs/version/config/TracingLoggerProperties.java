package com.wjh.common.logs.version.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
/**
 * 这个必须要设置装配，否则会报错
 */
@ConfigurationProperties(prefix = "com.wjh.log")
public class TracingLoggerProperties {
    private TracingConfig tracingConfig = new TracingConfig();
}
