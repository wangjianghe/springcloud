package com.wjh.common.logs.version.processor;

import org.aopalliance.intercept.MethodInvocation;

public interface MethodProcessor {
    Object process(MethodInvocation invocation,Class<?> targetClass) throws Throwable;
}
