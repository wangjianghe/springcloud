package com.wjh.common.logs.version.processor;

import com.alibaba.fastjson.JSON;
import com.wjh.common.core.util.WebUtils;
import lombok.AllArgsConstructor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.Marker;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@AllArgsConstructor
public class HttpRequestLoggableMethodProcessor extends AbstractLoggerMethodProcessor {

    private boolean enablePreLogging;
    private boolean enablePostLogging;
    private boolean enableErrorLogging;

    @Override
    protected boolean isLoggable(MethodInvocation invocation, Class<?> targetClass) {
        Method method = invocation.getMethod();
        if (!Modifier.isPublic(method.getModifiers())) {
            return false;
        }
        return true;
    }

    @Override
    protected Object preProcess(Logger log, Marker marker, MethodInvocation invocation) {
        if (enablePreLogging) {
            try {
                HttpServletRequest request = WebUtils.getRequest();
                String methodName = parseSimpleMethodNameFrom(invocation);
                String params = JSON.toJSONString(invocation.getArguments());
                log.info(marker, "requestMapping:[{}], methodName:[{}], Param:[{}]", request.getRequestURI(),
                        methodName,
                        params);
            } catch (Throwable e) {
            }
        }
        return null;
    }

    @Override
    protected void postProcess(Logger log, Marker marker, MethodInvocation invocation, Object o, long executeTime) {
        if (enablePostLogging) {
            try {
                String methodName = parseSimpleMethodNameFrom(invocation);
                String resp = JSON.toJSONString(o);
                log.info(marker, "method:[{}],executeTimes:[{}],response:[{}]", methodName, executeTime, resp);
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void errorProcess(Logger log, Marker marker, MethodInvocation invocation, Throwable throwable) {
        if (enableErrorLogging) {
            String methodName = parseSimpleMethodNameFrom(invocation);
            log.info(marker, "method:[{}],error:[{}]", methodName, throwable.getMessage());
        }
    }
}
