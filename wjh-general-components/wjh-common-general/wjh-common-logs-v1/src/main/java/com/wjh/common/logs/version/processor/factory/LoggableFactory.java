package com.wjh.common.logs.version.processor.factory;

import com.wjh.common.logs.version.processor.MethodProcessor;

/**
 * 日志生成工厂
 */
public interface LoggableFactory {
    MethodProcessor getMethodProcessor(boolean enablePreLogging,
                                       boolean enablePostLogging,
                                       boolean enableErrorLogging);
}
