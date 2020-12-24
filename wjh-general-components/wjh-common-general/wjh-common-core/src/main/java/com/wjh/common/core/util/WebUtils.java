package com.wjh.common.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class WebUtils extends org.springframework.web.util.WebUtils {
    private static final Logger log= LoggerFactory.getLogger(WebUtils.class);
    private static final String BASIC_ = "Basic ";
    private static final String UNKNOWN = "unknown";

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes==null){
            return null;
        }
        return attributes.getRequest();
    }
}
