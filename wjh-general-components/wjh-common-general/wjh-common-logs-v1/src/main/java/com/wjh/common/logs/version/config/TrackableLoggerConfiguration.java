package com.wjh.common.logs.version.config;

import com.wjh.common.logs.version.advisor.LoggerPointcutAdvisor;
import com.wjh.common.logs.version.interceptor.LoggerMethodInterceptor;
import com.wjh.common.logs.version.processor.factory.LoggableMethodProcessorFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@EnableConfigurationProperties(TracingLoggerProperties.class)
public class TrackableLoggerConfiguration {
    @Bean
    public LoggableMethodProcessorFactory loggableMethodProcessorFactory() {
        return new LoggableMethodProcessorFactory();
    }

    @Bean(name = "topeLoggerMethodInterceptor")
    public MethodInterceptor methodInterceptor(TracingLoggerProperties topeLoggerProperties,
                                               LoggableMethodProcessorFactory loggableMethodProcessorFactory) {
        return new LoggerMethodInterceptor(topeLoggerProperties, loggableMethodProcessorFactory);
    }

    @Bean
    public LoggerPointcutAdvisor loggerPointcutAdvisor(
            @Qualifier("topeLoggerMethodInterceptor") MethodInterceptor methodInterceptor) {
        LoggerPointcutAdvisor advisor = new LoggerPointcutAdvisor(methodInterceptor);
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }
}
