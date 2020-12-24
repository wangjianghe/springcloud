package com.wjh.common.logs.log;

import com.wjh.common.core.util.WebUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class LoggerMetHodInterceptor implements MethodInterceptor {

    private Marker marker = MarkerFactory.getMarker("BASIC");

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!isNeedPrintLogs(invocation)){
            return invocation.proceed();
        }
        Class<?> cls=getUserClassFrom(invocation);
        Logger log= LoggerFactory.getLogger(cls);
        try {
            HttpServletRequest request;
            request= WebUtils.getRequest();
            if (request!=null){
                log.info(marker,"RequestMapping: [{}], Param: [{}]", request.getRequestURI(),
                        Arrays.toString(invocation.getArguments()));
            }
            long start = System.currentTimeMillis();
            Object obj = invocation.proceed();
            long executeTime = System.currentTimeMillis() - start;
            String methodName = getSampleMethodName(invocation);
            log.info(marker, "Method: [{}] execute times: [{}], Response: [{}]", methodName, executeTime, obj);
            return obj;
        }catch (Exception e){
            String methodName = getSampleMethodName(invocation);
            // 只打印简单的信息, 其它信息交给业务方去处理打印
            log.error(marker, "Method: [{}] execute error: [{}]", methodName, e.getMessage());
            // 交给上层捕获, 这里不吞异常
            throw e;
        }
    }
    private String getSampleMethodName(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        return method.getName();
    }
    private boolean isNeedPrintLogs(MethodInvocation invocation){
        Method method=invocation.getMethod();
        if (!Modifier.isPublic(method.getModifiers())){
            return false;
        }
        Class<?> treeClass=getUserClassFrom(invocation);
        Method specificMethod=ClassUtils.getMostSpecificMethod(method,treeClass);
        specificMethod= BridgeMethodResolver.findBridgedMethod(specificMethod);
        return isContainLogPrintableComponentAnnotation(specificMethod);
    }

    private Class<?> getUserClassFrom(MethodInvocation invocation){
        return ClassUtils.getUserClass(invocation.getThis());
    }

    private boolean isContainLogPrintableComponentAnnotation(Method method){
        Class<?> cls=method.getDeclaringClass();
        AnnotationAttributes comp= AnnotatedElementUtils.getMergedAnnotationAttributes(cls, Component.class);
        //微服务的feign接口调用
        AnnotationAttributes fc=AnnotatedElementUtils.getMergedAnnotationAttributes(cls, FeignClient.class);
        return comp!=null|| fc!=null;
    }
}
