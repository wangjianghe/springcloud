package com.wjh.common.logs.version.interceptor;

import com.wjh.common.core.util.WebUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class FeignLogRequestInterceptor extends AbstractLogTemplatePopulator implements RequestInterceptor {
    @Override
    protected void putIfNotNull(Object container, String key, String value) {
        log.info("feign request");
        RequestTemplate requestTemplate= (RequestTemplate) container;
        requestTemplate.header(key,value);
    }

    @Override
    public void apply(RequestTemplate template) {
        try {
            HttpServletRequest request= WebUtils.getRequest();
            if (request==null){
                return;
            }
            populate(request,template);
        }catch (Exception e){
            log.info("日志拦截器错误");
        }
    }
}
