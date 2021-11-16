package com.wjh.common.logs.version.interceptor;

import com.wjh.common.logs.version.config.TracingConfig;
import com.wjh.common.logs.version.config.TracingLoggerProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MDCImproveInterceptor extends AbstractLogTemplatePopulator implements HandlerInterceptor {
    private TracingLoggerProperties tracingLoggerProperties;

    public MDCImproveInterceptor(TracingLoggerProperties tracingLoggerProperties) {
        this.tracingLoggerProperties = tracingLoggerProperties;
    }

    @Override
    protected void putIfNotNull(Object container, String key, String value) {
        if (StringUtils.isNotEmpty(key)){
            MDC.put(key,value);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        populate(request,null);
        TracingConfig config=tracingLoggerProperties.getTracingConfig();
        if (!config.isTraceIdLog()){
            String traceId=MDC.get("traceId");
            putIfNotNull(null,"traceId",traceId);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
