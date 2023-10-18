package com.wjh.wjhcom.common.mq.rocket.config;

import com.wjh.wjhcom.common.mq.rocket.constants.SelectorType;
import lombok.Data;
import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;

@Data
public class RocketMqConsumerConfig {
    private String group;
    private String topic;
    private String instanceName;
    private MessageModel messageModel= MessageModel.CLUSTERING;
    private SelectorType selectorType=SelectorType.TAG;
    private String selectorExpression="*";
}
