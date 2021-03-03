package com.wjh.wjhcom.common.mq.rocket.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TestMessage {
    String testValue() default "values";
}
