package com.wjh.common.logs.config;

import com.wjh.common.logs.log.LoggerPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class TrackableLoggerConfiguration {
    @Bean
    public LoggerPointcutAdvisor loggerPointcutAdvisor(){
        LoggerPointcutAdvisor advisor=new LoggerPointcutAdvisor();
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }

}
