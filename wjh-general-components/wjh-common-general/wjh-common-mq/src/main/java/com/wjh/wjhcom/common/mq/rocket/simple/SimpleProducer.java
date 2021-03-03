package com.wjh.wjhcom.common.mq.rocket.simple;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

public class SimpleProducer {
    private String producerGroup;
    private String address;

    private String instanceName;

    private DefaultMQProducer producer;

    private DefaultMQProducer getProducer(){
        return producer;
    }

    public SimpleProducer(String producerGroup, String address) {
        this.producerGroup = producerGroup;
        this.address = address;
    }
    public void init() throws MQClientException {
        producer=new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(address);
        if (instanceName!=null){
            producer.setInstanceName(instanceName);
        }
        producer.start();
    }
    public void destroy(){
        producer.shutdown();
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
}
