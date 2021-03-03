package com.wjh.wjhcom.common.mq.rocket.core.factory;

import org.apache.rocketmq.client.exception.MQClientException;

import java.util.Properties;

public interface MqClientFactory {
    /**
     * 创建发送端对象
     * @return
     */
    Object createProducer(Properties properties,Object annotationObject) throws Exception;

    /**
     * 创建消费端对象
     * @param properties
     * @return
     */
    Object createConsumer(Properties properties,Object o) throws Exception;
}
