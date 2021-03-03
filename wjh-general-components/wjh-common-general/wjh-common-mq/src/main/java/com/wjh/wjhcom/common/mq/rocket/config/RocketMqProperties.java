package com.wjh.wjhcom.common.mq.rocket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
@ConfigurationProperties(prefix = "wjh.mq.rocket")
public class RocketMqProperties {
    private String address;
}
