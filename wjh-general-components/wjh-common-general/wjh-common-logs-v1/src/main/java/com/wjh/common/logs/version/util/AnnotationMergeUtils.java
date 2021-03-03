package com.wjh.common.logs.version.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhaodi
 */
public abstract class AnnotationMergeUtils {

    private static final Set<Class<? extends Annotation>> CONTROLLER_ANNOTATIONS = new HashSet<>();
    private static final Set<Class<? extends Annotation>> SERVICE_ANNOTATIONS = new HashSet<>();
    private static final Set<Class<? extends Annotation>> FEIGN_ANNOTATIONS = new HashSet<>();

    static {
        CONTROLLER_ANNOTATIONS.add(Controller.class);
        CONTROLLER_ANNOTATIONS.add(RequestMapping.class);
        SERVICE_ANNOTATIONS.add(Service.class);
        FEIGN_ANNOTATIONS.add(FeignClient.class);
    }

    public static Set<Class<? extends Annotation>> getMergedControllerAnnotations() {
        return CONTROLLER_ANNOTATIONS;
    }

    public static Set<Class<? extends Annotation>> getMergedServiceAnnotations() {
        return SERVICE_ANNOTATIONS;
    }

    public static Set<Class<? extends Annotation>> getMergedFeignAnnotations() {
        return FEIGN_ANNOTATIONS;
    }

}
