package com.wjh.common.logs.version.util;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: general-components
 * @description: 类解析
 * @author: zhaodi
 **/
public abstract class ClassParseUtils {

    /**
     * 获取目标类
     * @param invocation invocation
     * @return 目标类
     */
    public static Class<?> getTargetClass(MethodInvocation invocation) {
        Class<?> rawClass = invocation.getThis().getClass();
        // 处理通过Proxy#getProxy()生成的代理类, 主要是针对feign
        if (Proxy.isProxyClass(rawClass)) {
            // 尝试简单解析
            Class<?> dummyClass = ClassUtils.getUserClass(invocation.getThis());
            if (Proxy.isProxyClass(dummyClass)) {
                // 使用JDK解析
                return tryParseJdkProxyClass(invocation);
            }

        }
        // 简单处理
        // 暂时不考虑桥接等模式
        return ClassUtils.getUserClass(invocation.getThis());
    }

    private static Class<?> tryParseJdkProxyClass(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        return method.getDeclaringClass();
    }

}
