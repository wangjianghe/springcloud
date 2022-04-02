package com.wjh.wjhcom.common.mq.rocket.annotation;

import com.wjh.wjhcom.common.mq.rocket.callback.DefaultTransactionCheckListener;
import com.wjh.wjhcom.common.mq.rocket.core.bean.RocketMessageTypeEnum;
import com.wjh.wjhcom.common.mq.rocket.core.bean.TransactionParamInfo;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
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
    RocketMessageTypeEnum messageType() default RocketMessageTypeEnum.COMMON_MESSAGE;
    Class<? extends TransactionCheckListener> transactionCheckListener() default DefaultTransactionCheckListener.class;
    int checkThreadPoolMinSize() default 1;
    int checkThreadPoolMaxSize() default 1;
    int checkRequestHoldMax() default 2000;
}
