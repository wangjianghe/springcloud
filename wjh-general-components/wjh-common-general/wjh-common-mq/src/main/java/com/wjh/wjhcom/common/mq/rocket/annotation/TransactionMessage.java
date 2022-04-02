package com.wjh.wjhcom.common.mq.rocket.annotation;

import com.wjh.wjhcom.common.mq.rocket.callback.DefaultSendCallback;
import com.wjh.wjhcom.common.mq.rocket.constants.MessageSendType;
import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.SendCallback;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TransactionMessage {
    String topic() default "";
    String tag() default "*";
    Class<? extends LocalTransactionExecuter> executer();
    String arg();
}
