package com.wjh.wjhcom.common.mq.rocket.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

@Slf4j
public class DefaultSendCallback implements SendCallback {
    @Override
    public void onSuccess(SendResult sendResult) {
        log.info("message send successfully sendResult {}",sendResult);
    }

    @Override
    public void onException(Throwable throwable) {
        log.error("failed to send message context",throwable);
    }
}
