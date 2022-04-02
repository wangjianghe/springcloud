package com.wjh.wjhcom.common.mq.rocket.aspect;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.lang.annotation.Target;
import java.util.Map;

@Aspect()
@Slf4j
@Data
public class RocketAspect2 {
    private Map<String,Object> consumerMap;

    public RocketAspect2(Map<String, Object> consumerMap) {
        this.consumerMap = consumerMap;
    }

    @Pointcut("@annotation(com.wjh.wjhcom.common.mq.rocket.annotation.CommonMessage)")
    public void commonMessagePointcut(){log.info("start sending commonMessage");}
    @Pointcut("execution(public * com.wjh.wjhcom.common.mq.rocket.init.RocketMqInitLoadListener.*(..))")
    public void testCommon(){
        log.info("testCommon");
    }
    @Around("commonMessagePointcut()")
    public Object commonMessageAround(ProceedingJoinPoint point) throws Throwable {
        Object o=point.proceed();
        return o;
    }
    @Before("testCommon()") //前置通知：在目标方法执行前调用
    public void before(){
        log.info("before");
    }
    @After("testCommon()") //后置通知：在目标方法执行后调用，若目标方法出现异常，则不执行
    public void after(){
        log.info("after");
    }
}
