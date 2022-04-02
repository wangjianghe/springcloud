package com.wjh.wjhcom.common.mq.rocket.core.factory;

import com.wjh.wjhcom.common.mq.rocket.annotation.RocketMessage;
import com.wjh.wjhcom.common.mq.rocket.annotation.RocketMqMsgListener;
import com.wjh.wjhcom.common.mq.rocket.constants.SelectorType;
import com.wjh.wjhcom.common.mq.rocket.core.bean.RocketMessageTypeEnum;
import com.wjh.wjhcom.common.mq.rocket.core.bean.TransactionParamInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.util.StringUtils;

import java.util.Properties;

@Slf4j
public class RocketMqClientFactory implements MqClientFactory {
    @Override
    public Object createProducer(Properties properties,Object annotationObject) throws MQClientException, InstantiationException, IllegalAccessException {
        RocketMessage messageAnnotation=annotationObject.getClass().getAnnotation(RocketMessage.class);
        DefaultMQProducer producer;
        String groupId=messageAnnotation.groupId();
        if (messageAnnotation.messageType()==RocketMessageTypeEnum.TRANSACTION_MESSAGE){
            producer=createTransactionProduct(messageAnnotation,groupId);
        }else{
            producer=new DefaultMQProducer(groupId);
        }
        String address=properties.getProperty("address");
        String instanceName=messageAnnotation.instanceName();
        producer.setNamesrvAddr(address);
        if (!StringUtils.isEmpty(instanceName)){
            producer.setInstanceName(instanceName);
        }
        producer.start();
        return producer;
    }
    public DefaultMQProducer createTransactionProduct(RocketMessage message,String groupId) throws IllegalAccessException, InstantiationException {
        TransactionMQProducer producer=new TransactionMQProducer(groupId);
        producer.setTransactionCheckListener(message.transactionCheckListener().newInstance());
        producer.setCheckThreadPoolMinSize(message.checkThreadPoolMinSize());
        producer.setCheckThreadPoolMaxSize(message.checkThreadPoolMaxSize());
        producer.setCheckRequestHoldMax(message.checkRequestHoldMax());
        return producer;
    }

    @Override
    public Object createConsumer(Properties properties,Object consumerObject) throws Exception {
        boolean hasAnnotation=consumerObject.getClass().isAnnotationPresent(RocketMqMsgListener.class);
        if (!hasAnnotation){
            throw new Exception("obj not have rocketMqMsgListener");
        }
        RocketMqMsgListener rocketMqMsgListener=consumerObject.getClass().getAnnotation(RocketMqMsgListener.class);
        DefaultMQPushConsumer consumer;
        String address=properties.getProperty("address");
        String group=rocketMqMsgListener.group();
        String instanceName=rocketMqMsgListener.instanceName();
        String topic=rocketMqMsgListener.topic();
        SelectorType selectorType=rocketMqMsgListener.selectorType();
        String selectorExpression=rocketMqMsgListener.selectorExpression();
        MessageModel messageModel=rocketMqMsgListener.messageModel();
        consumer=new DefaultMQPushConsumer(group);
        consumer.setNamesrvAddr(address);
        if (!StringUtils.isEmpty(instanceName)){
            consumer.setInstanceName(instanceName);
        }
        if (SelectorType.SQL92==selectorType){
            consumer.subscribe(topic, MessageSelector.bySql(selectorExpression));
        }else{
            consumer.subscribe(topic,selectorExpression);
        }
        consumer.setMessageModel(messageModel);
        if (consumerObject instanceof MessageListenerConcurrently){
            consumer.registerMessageListener((MessageListenerConcurrently)consumerObject);
        }else if (consumerObject instanceof MessageListenerOrderly){
            consumer.registerMessageListener((MessageListenerOrderly)consumerObject);
        }else{
            throw new Exception("consumerObject is error type");
        }
        consumer.start();
        log.info("wjh-rocketmq-consumerStart-"+topic);
        return consumer;
    }
}
