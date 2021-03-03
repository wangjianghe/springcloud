package com.wjh.common.logs.version.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

public class LoggerPointcutAdvisor extends AbstractPointcutAdvisor {
    private final Advice advice;
    private final Pointcut pointcut;

    public LoggerPointcutAdvisor(Advice advice){
        this.advice=advice;
        this.pointcut=buildPointcut();
    }
    public LoggerPointcutAdvisor(Advice advice,Pointcut pointcut){
        this.advice=advice;
        this.pointcut=pointcut;
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
        return new ComposablePointcut(comp).union(fc).intersection(fc);
    }
}
