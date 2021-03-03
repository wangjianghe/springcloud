package com.wjh.common.logs.version.processor;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.lang.reflect.Method;

public abstract class AbstractLoggerMethodProcessor implements MethodProcessor {
    private Marker marker= MarkerFactory.getMarker("BASIC");
    @Override
    public Object process(MethodInvocation invocation, Class<?> targetClass) throws Throwable {
        if (!isLoggable(invocation,targetClass)){
            return invocation.proceed();
        }
        Logger logger= LoggerFactory.getLogger(targetClass);
        try {
            Object o=preProcess(logger,marker,invocation);
            if (o!=null){
                return o;
            }
            long start=System.currentTimeMillis();
            Object processObj=invocation.proceed();
            long speedTime=System.currentTimeMillis()-start;
            postProcess(logger,marker,invocation,processObj,speedTime);
            return processObj;
        }catch (Exception e){
            errorProcess(logger,marker,invocation,e);
            throw e;
        }
    }
    protected abstract boolean isLoggable(MethodInvocation invocation,Class<?> targetClass);
    protected abstract Object preProcess(Logger log, Marker marker,MethodInvocation invocation);
    protected abstract void postProcess(Logger log, Marker marker,MethodInvocation invocation,Object o,long executeTime);
    protected abstract void errorProcess(Logger log,Marker marker,MethodInvocation invocation,Throwable throwable);
    protected String parseSimpleMethodNameFrom(MethodInvocation methodInvocation){
        Method method=methodInvocation.getMethod();
        return method.getName();
    }
}
