package com.wjh.wjhcom.common.mq.rocket.core.factory;

import com.wjh.wjhcom.common.mq.rocket.annotation.RocketMessage;
import com.wjh.wjhcom.common.mq.rocket.thread.AbstractSendMessageThread;

public class SendMessageFactoryExecution extends AbstractSendMessageThread {

    public SendMessageFactoryExecution(RocketMessage rocketMessage, Object message, byte[] bytes) {
        super(rocketMessage, message, bytes);
    }

    @Override
    protected void startSendMessage(RocketMessage rocketMessage, Object message, byte[] bytes) {

    }
}
