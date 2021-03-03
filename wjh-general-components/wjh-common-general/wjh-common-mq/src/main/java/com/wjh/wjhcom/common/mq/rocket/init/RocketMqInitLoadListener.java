package com.wjh.wjhcom.common.mq.rocket.init;

import com.wjh.wjhcom.common.mq.rocket.annotation.RocketMqMsgListener;
import com.wjh.wjhcom.common.mq.rocket.config.RocketMqProperties;
import com.wjh.wjhcom.common.mq.rocket.core.factory.MqClientFactory;
import com.wjh.wjhcom.common.mq.rocket.core.factory.RocketMqClientFactory;
import lombok.SneakyThrows;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.*;

public class RocketMqInitLoadListener implements ApplicationListener<ContextRefreshedEvent>, DisposableBean {
    private Map<String,Object> beanMap=new HashMap<>();
    private List<Object> consumerList=new ArrayList<>();
    private Properties properties=new Properties();
    private RocketMqProperties rocketMqProperties;

    public RocketMqInitLoadListener(RocketMqProperties rocketMqProperties) {
        this.rocketMqProperties = rocketMqProperties;
    }

    @Override
    public void destroy() throws Exception {
        for (Object consumer:consumerList){
            if (consumer==null){
                continue;
            }
            if (consumer instanceof DefaultMQPushConsumer){
                DefaultMQPushConsumer mqPushConsumer= (DefaultMQPushConsumer) consumer;
                mqPushConsumer.shutdown();
            }
        }
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent()!=null){
            return;
        }
        beanMap=event.getApplicationContext().getBeansWithAnnotation(RocketMqMsgListener.class);
        if (beanMap.size() == 0){
            return;
        }
        for (Object bean:beanMap.values()){
            createConsumer(bean,rocketMqProperties);
        }
    }
    private Object createConsumer(Object bean, RocketMqProperties rocketMqProperties) throws Exception {
        MqClientFactory factory=new RocketMqClientFactory();
        properties.setProperty("address",rocketMqProperties.getAddress());
        return factory.createConsumer(properties,bean);
    }
}
