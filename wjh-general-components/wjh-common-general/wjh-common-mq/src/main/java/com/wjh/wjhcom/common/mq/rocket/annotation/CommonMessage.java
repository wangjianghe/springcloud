package com.wjh.wjhcom.common.mq.rocket.annotation;

import com.wjh.wjhcom.common.mq.rocket.callback.DefaultSendCallback;
import com.wjh.wjhcom.common.mq.rocket.constants.MessageSendType;
import org.apache.rocketmq.client.producer.SendCallback;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CommonMessage {
    String topic() default "";
    String tag() default "*";
    MessageSendType messageSendType() default MessageSendType.SEND_ASYNC;
    Class<? extends SendCallback> callback() default DefaultSendCallback.class;
    /**
     * 延迟等级 messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
     * @return
     */
    int delayTimeLevel() default 0;
    /**
     * 延后多少秒 60 60秒
     * @return
     */
    int delayTimeSec() default 0;
    /**
     * 以后多少毫秒(ms) 60000  延后60秒
     * @return
     */
    int delayTimeMs() default 0;

    /**
     * 指定的时间，message.setDeliverTimeMs(System.currentTimeMillis() + 10000L); 延后10秒
     * 当前时间延后10秒
     * @return
     */
    long deliverTimeMs() default 0;


}
