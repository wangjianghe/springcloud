package com.wjh.common.logs.version.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@WebFilter(filterName = "traceIdFilter",urlPatterns = "/*")
@Order(0)
@Component
public class TraceIdFilter implements Filter {
    public static final String TRACE_ID="traceId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("这个是过滤器初始");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
        HeaderMapRequestWrapper wrapper=new HeaderMapRequestWrapper(httpServletRequest);
        String traceId=httpServletRequest.getHeader("traceId");
        if (StringUtils.isEmpty(traceId)){
            String uuid=UUID.randomUUID().toString();
            wrapper.addHeader("traceId",uuid);
            MDC.put(TRACE_ID, uuid);
        }
        chain.doFilter(wrapper,response);
    }

    @Override
    public void destroy() {
        MDC.clear();
    }
}

