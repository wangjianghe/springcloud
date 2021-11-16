package com.wjh.common.logs.version.config;

import com.wjh.common.logs.version.advisor.LoggerPointcutAdvisor;
import com.wjh.common.logs.version.interceptor.FeignLogRequestInterceptor;
import com.wjh.common.logs.version.interceptor.LoggerMethodInterceptor;
import com.wjh.common.logs.version.interceptor.MDCImproveInterceptor;
import com.wjh.common.logs.version.processor.factory.LoggableMethodProcessorFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

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
    @Bean
    public FeignLogRequestInterceptor feignLogRequestInterceptor(){
        return new FeignLogRequestInterceptor();
    }

    @Bean
    public MDCImproveInterceptor mdcImproveInterceptor(TracingLoggerProperties tracingLoggerProperties){
        return new MDCImproveInterceptor(tracingLoggerProperties);
    }

    @Slf4j
    @Configuration
    @AllArgsConstructor
    public static class LoggerWebMvcConfig implements WebMvcConfigurer{
        private MDCImproveInterceptor mdcImproveInterceptor;

        @PostConstruct
        public void init(){
            log.info("我的mvcInterceptor init");
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(mdcImproveInterceptor).order(Ordered.LOWEST_PRECEDENCE);
        }
    }
}
