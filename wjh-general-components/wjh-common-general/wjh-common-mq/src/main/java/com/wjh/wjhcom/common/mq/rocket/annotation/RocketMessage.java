package com.wjh.wjhcom.common.mq.rocket.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Component
public @interface RocketMessage {
    String groupId();
    String instanceName();
}
