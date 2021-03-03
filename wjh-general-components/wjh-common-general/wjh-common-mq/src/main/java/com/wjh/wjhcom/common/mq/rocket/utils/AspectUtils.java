package com.wjh.wjhcom.common.mq.rocket.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class AspectUtils {
    private AspectUtils() {
    }

    private static Method getMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        return signature.getMethod();
    }

    public static <T extends Annotation> T getAnnotation(ProceedingJoinPoint proceedingJoinPoint, Class<T> annotationClass) {
        return getMethod(proceedingJoinPoint).getAnnotation(annotationClass);
    }

    public static <T extends Annotation> T getDeclaringClassAnnotation(ProceedingJoinPoint proceedingJoinPoint, Class<T> annotationClass) {
        return getMethod(proceedingJoinPoint).getDeclaringClass().getAnnotation(annotationClass);
    }

    public static Parameter[] getParams(ProceedingJoinPoint proceedingJoinPoint) {
        Method method = getMethod(proceedingJoinPoint);
        return method.getParameters();
    }
}
