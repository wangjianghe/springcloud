package com.wjh.common.logs.version.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

@Slf4j
public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public HeaderMapRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    private Map<String,String> headerMap=new HashMap<>();

    public void addHeader(String name,String value){
        headerMap.put(name,value);
    }

    @Override
    public String getHeader(String name) {
        log.info("这个是我自己的获取请求头："+name);
        String headerValue=super.getHeader(name);
        if (headerMap.containsKey(name)){
            headerValue=headerMap.get(name);
        }
        return headerValue;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        log.info("自己的headerNames");
        List<String> names= Collections.list(super.getHeaderNames());
        for (String name:headerMap.keySet()){
            names.add(name);
        }
        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        log.info("自己的获取header头："+name);
        List<String> values= Collections.list(super.getHeaders(name));
        if (headerMap.containsKey(name)){
            values=Arrays.asList(headerMap.get(name));
        }
        return Collections.enumeration(values);
    }
}
