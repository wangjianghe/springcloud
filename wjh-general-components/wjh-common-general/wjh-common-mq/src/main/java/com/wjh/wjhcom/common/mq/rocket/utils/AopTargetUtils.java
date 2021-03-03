package com.wjh.wjhcom.common.mq.rocket.utils;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.io.File;
import java.lang.reflect.Field;

public class AopTargetUtils {
    public static Object getTarget(Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)){
            return proxy;
        }
        if (AopUtils.isJdkDynamicProxy(proxy)){
            return getJdkDynamicProxy(proxy);
        }
        if (AopUtils.isCglibProxy(proxy)){
            return getCglibProxy(proxy);
        }
        //todo 缺少一个桥接
        return proxy;
    }
    public static Object getJdkDynamicProxy(Object proxy) throws Exception {
        Field h=proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy= (AopProxy) h.get(proxy);
        Field advised=aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return ((AdvisedSupport)advised.get(aopProxy)).getTargetSource().getTarget();
    }
    public static Object getCglibProxy(Object proxy) throws Exception {
        Field h=proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object daynamicAdvisedInterceptor=h.get(proxy);
        Field advised=daynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return ((AdvisedSupport)advised.get(daynamicAdvisedInterceptor)).getTargetSource().getTarget();
    }
}
