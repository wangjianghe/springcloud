package com.wjh.common.logs.annotation;

import com.wjh.common.logs.config.TrackableLoggerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TrackableLoggerConfiguration.class)
public @interface EnableTrackableLogger {
}
