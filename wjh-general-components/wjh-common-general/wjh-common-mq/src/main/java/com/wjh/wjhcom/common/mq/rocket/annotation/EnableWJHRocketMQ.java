package com.wjh.wjhcom.common.mq.rocket.annotation;


import com.wjh.wjhcom.common.mq.rocket.config.RocketMqConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import({RocketMqConfiguration.class})
public @interface EnableWJHRocketMQ {
}
