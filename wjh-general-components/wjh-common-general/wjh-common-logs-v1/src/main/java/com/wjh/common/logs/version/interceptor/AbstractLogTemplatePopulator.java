package com.wjh.common.logs.version.interceptor;

import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractLogTemplatePopulator {
    protected void populate(HttpServletRequest request,Object container){
        if (request!=null){
            String from =request.getHeader("from");
            putIfNotNull(container,"from",from);
            String versionCode=request.getHeader("versionCode");
            putIfNotNull(container,"versionCode",versionCode);
            String traceId=request.getHeader("traceId");
            putIfNotNull(container,"traceId",traceId);
        }
    }
    protected abstract  void putIfNotNull(Object container,String key,String value);
}
