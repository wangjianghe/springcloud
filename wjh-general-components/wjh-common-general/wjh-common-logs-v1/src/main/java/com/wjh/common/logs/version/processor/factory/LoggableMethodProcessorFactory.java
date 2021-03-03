package com.wjh.common.logs.version.processor.factory;

import com.wjh.common.logs.version.processor.HttpRequestLoggableMethodProcessor;
import com.wjh.common.logs.version.processor.MethodProcessor;

public class LoggableMethodProcessorFactory implements LoggableFactory {
    @Override
    public MethodProcessor getMethodProcessor(boolean enablePreLogging, boolean enablePostLogging, boolean enableErrorLogging) {
        return new HttpRequestLoggableMethodProcessor(enablePreLogging,enablePostLogging,enableErrorLogging);
    }
}
