package com.wjh.common.logs.log;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

public class LoggerPointcutAdvisor extends AbstractPointcutAdvisor {
    private final Advice advice;
    private final Pointcut pointcut;

    public LoggerPointcutAdvisor() {
        this.advice=new LoggerMetHodInterceptor();
        this.pointcut=buildPointcut();
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    private Pointcut buildPointcut(){
        Pointcut comp=new AnnotationMatchingPointcut(Component.class,true);
        Pointcut fc=new AnnotationMatchingPointcut(FeignClient.class,true);
        return new ComposablePointcut(comp).union(fc);
    }
}
