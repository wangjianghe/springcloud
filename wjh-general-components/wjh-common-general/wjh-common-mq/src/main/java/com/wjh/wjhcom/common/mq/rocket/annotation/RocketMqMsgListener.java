package com.wjh.wjhcom.common.mq.rocket.annotation;

import com.wjh.wjhcom.common.mq.rocket.constants.SelectorType;
import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RocketMqMsgListener {
    String topic();
    String group();
    String instanceName();
    MessageModel messageModel() default MessageModel.CLUSTERING;
    SelectorType selectorType() default SelectorType.TAG;
    String selectorExpression() default "*";
}
