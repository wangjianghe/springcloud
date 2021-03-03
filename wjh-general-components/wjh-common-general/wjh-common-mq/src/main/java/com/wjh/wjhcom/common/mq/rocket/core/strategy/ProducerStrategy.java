package com.wjh.wjhcom.common.mq.rocket.core.strategy;

import com.wjh.wjhcom.common.mq.rocket.annotation.CommonMessage;
import com.wjh.wjhcom.common.mq.rocket.annotation.RocketMessage;

public class ProducerStrategy {
    public ProducerStrategy() {
    }
    public static void startSendMessage(Long startDeliverTime, RocketMessage rocketMessage,Object message,
                                        byte[] bytes){
        if (message instanceof CommonMessage){
            CommonMessage commonMessage= (CommonMessage) message;

        }
    }
}
