package com.wjh.wjhcom.common.mq.rocket.container;

import com.wjh.wjhcom.common.mq.rocket.annotation.RocketMqMsgListener;
import com.wjh.wjhcom.common.mq.rocket.config.RocketMqProperties;
import com.wjh.wjhcom.common.mq.rocket.core.factory.MqClientFactory;
import com.wjh.wjhcom.common.mq.rocket.core.factory.RocketMqClientFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RocketConsumerContainer implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private Map<String,Object> beanMap=new HashMap<>();
    private Properties properties=new Properties();
    private RocketMqProperties rocketMqProperties;

    public RocketConsumerContainer(RocketMqProperties rocketMqProperties) {
        this.rocketMqProperties = rocketMqProperties;
    }

    @PostConstruct
    public void initialize() throws Exception {
        if (this.applicationContext==null){
            return;
        }
        beanMap=this.applicationContext.getBeansWithAnnotation(RocketMqMsgListener.class);
        if (beanMap.size() == 0){
            return;
        }
        for (Object bean:beanMap.values()){
            createConsumer(bean,rocketMqProperties);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private Object createConsumer(Object bean, RocketMqProperties rocketMqProperties) throws Exception {
        MqClientFactory factory=new RocketMqClientFactory();
        properties.setProperty("address",rocketMqProperties.getAddress());
        return factory.createConsumer(properties,bean);
    }
}
