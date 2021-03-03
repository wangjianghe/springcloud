package com.wjh.common.logs.version.interceptor;

import com.wjh.common.logs.version.config.TracingConfig;
import com.wjh.common.logs.version.config.TracingLoggerProperties;
import com.wjh.common.logs.version.processor.MethodProcessor;
import com.wjh.common.logs.version.processor.factory.LoggableMethodProcessorFactory;
import com.wjh.common.logs.version.util.AnnotationMergeUtils;
import com.wjh.common.logs.version.util.ClassParseUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.annotation.Annotation;
import java.util.Set;

public class LoggerMethodInterceptor implements MethodInterceptor {

    private TracingLoggerProperties loggerProperties;
    private LoggableMethodProcessorFactory loggableMethodProcessorFactory;

    public LoggerMethodInterceptor(TracingLoggerProperties loggerProperties, LoggableMethodProcessorFactory loggableMethodProcessorFactory) {
        this.loggerProperties = loggerProperties;
        this.loggableMethodProcessorFactory = loggableMethodProcessorFactory;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> targetClass= ClassParseUtils.getTargetClass(invocation);
        MethodProcessor methodProcessor=getMethodProcessor(invocation,loggerProperties,targetClass);
        if (methodProcessor==null){
            return invocation.proceed();
        }
        return methodProcessor.process(invocation,targetClass);
    }

    public MethodProcessor getMethodProcessor(MethodInvocation invocation,
                                              TracingLoggerProperties tracingLoggerProperties,
                                              Class<?> cls){
        TracingConfig tracingConfig=tracingLoggerProperties.getTracingConfig();
        boolean exists;
        if (tracingConfig.getController().isEnable()){
            exists=isIncludeAnnotation(cls, AnnotationMergeUtils.getMergedControllerAnnotations());
            if (exists){
                return getLoggableMethodProcessor(tracingConfig.getController());
            }
        }
        if (tracingConfig.getService().isEnable()){
            exists=isIncludeAnnotation(cls,AnnotationMergeUtils.getMergedServiceAnnotations());
            if (exists){
                return getLoggableMethodProcessor(tracingConfig.getService());
            }
        }
        if (tracingConfig.getFeign().isEnable()){
            exists=isIncludeAnnotation(cls,AnnotationMergeUtils.getMergedFeignAnnotations());
            if (exists){
                return getLoggableMethodProcessor(tracingConfig.getFeign());
            }
        }
        return null;
    }
    public MethodProcessor getLoggableMethodProcessor(TracingConfig.BasicConfig config){
        return loggableMethodProcessorFactory.getMethodProcessor(config.isEnablePreLogging()
                ,config.isEnablePostLogging()
                ,config.isEnableErrorLogging());
    }
    private boolean isIncludeAnnotation(Class<?> cls, Set<Class<? extends Annotation>> annotations){
        for (Class<? extends Annotation> annotation:annotations){
            AnnotationAttributes attributes= AnnotatedElementUtils.getMergedAnnotationAttributes(cls,annotation);
            if (attributes!=null){
                return true;
            }
        }
        return false;
    }



}
