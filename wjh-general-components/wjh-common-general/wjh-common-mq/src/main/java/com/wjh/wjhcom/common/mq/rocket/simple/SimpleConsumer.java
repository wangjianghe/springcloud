package com.wjh.wjhcom.common.mq.rocket.simple;


import com.wjh.wjhcom.common.mq.rocket.config.RocketMqConsumerConfig;
import com.wjh.wjhcom.common.mq.rocket.config.RocketMqProperties;
import com.wjh.wjhcom.common.mq.rocket.constants.SelectorType;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;

public class SimpleConsumer {
    private DefaultMQPushConsumer consumer;
    private RocketMqProperties rocketMqProperties;
    private RocketMqConsumerConfig consumerConfig;
    private MessageListenerConcurrently messageListener;

    public SimpleConsumer(RocketMqProperties rocketMqProperties, RocketMqConsumerConfig consumerConfig, MessageListenerConcurrently messageListener) {
        this.rocketMqProperties = rocketMqProperties;
        this.consumerConfig = consumerConfig;
        this.messageListener = messageListener;
    }

    public void init() throws MQClientException {
        consumer=new DefaultMQPushConsumer(consumerConfig.getGroup());
        consumer.setNamesrvAddr(rocketMqProperties.getAddress());
        if (consumerConfig.getInstanceName()!=null){
            consumer.setInstanceName(consumerConfig.getInstanceName());
        }
        if (consumerConfig.getSelectorType()== SelectorType.TAG){
            consumer.subscribe(consumerConfig.getTopic(), MessageSelector.bySql(consumerConfig.getSelectorExpression()));
        }else{
            consumer.subscribe(consumerConfig.getTopic(),consumerConfig.getSelectorExpression());
        }
        consumer.setMessageModel(consumerConfig.getMessageModel());
        consumer.registerMessageListener(messageListener);
        consumer.start();
    }
    public void destroy(){
        consumer.shutdown();
    }
    public DefaultMQPushConsumer getConsumer(){
        return consumer;
    }
}
