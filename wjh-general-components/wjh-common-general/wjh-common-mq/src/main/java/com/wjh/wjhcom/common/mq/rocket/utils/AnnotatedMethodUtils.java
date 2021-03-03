package com.wjh.wjhcom.common.mq.rocket.utils;

import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

public class AnnotatedMethodUtils {
    private AnnotatedMethodUtils() {
    }

    /**
     * 查询对象下的某个注解的所有方法
     * @param bean 要查询的对象
     * @param annotation 注解对象
     * @param <T>
     * @return
     */
/*    public static <T extends Annotation> Map<Method, T> getMethodAndAnnotation(Object bean, Class<T> annotation) {
        return MethodIntrospector.selectMethods(bean.getClass(),
                (MethodIntrospector.MetadataLookup<T>) method -> AnnotatedElementUtils
                        .findMergedAnnotation(method, annotation));
    }*/
    public static <T extends Annotation> Map<Method, T> getMethodAndAnnotation(Object bean, Class<T> annotation) {

        return MethodIntrospector.selectMethods(bean.getClass(),
                (MethodIntrospector.MetadataLookup<T>) method -> AnnotatedElementUtils
                        .findMergedAnnotation(method, annotation));
    }
}
