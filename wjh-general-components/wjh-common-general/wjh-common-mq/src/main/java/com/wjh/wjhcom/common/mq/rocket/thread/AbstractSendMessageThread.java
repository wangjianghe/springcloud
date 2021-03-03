package com.wjh.wjhcom.common.mq.rocket.thread;

import com.wjh.wjhcom.common.mq.rocket.annotation.RocketMessage;

public abstract class AbstractSendMessageThread implements Runnable {
    private RocketMessage rocketMessage;
    private Object message;
    private byte[] bytes;

    public AbstractSendMessageThread(RocketMessage rocketMessage, Object message, byte[] bytes) {
        this.rocketMessage = rocketMessage;
        this.message = message;
        this.bytes = bytes;
    }
    protected abstract void startSendMessage(RocketMessage rocketMessage, Object message, byte[] bytes);
    @Override
    public void run() {
        startSendMessage(rocketMessage,message,bytes);
    }
}
