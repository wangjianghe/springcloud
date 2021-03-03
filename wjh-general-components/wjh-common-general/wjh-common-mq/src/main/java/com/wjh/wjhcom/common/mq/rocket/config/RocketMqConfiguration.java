package com.wjh.wjhcom.common.mq.rocket.config;


import com.wjh.wjhcom.common.mq.rocket.aspect.RocketAspect;
import com.wjh.wjhcom.common.mq.rocket.container.RocketConsumerContainer;
import com.wjh.wjhcom.common.mq.rocket.container.RocketProducerContainer;
import com.wjh.wjhcom.common.mq.rocket.init.RocketMqInitLoadListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(RocketMqProperties.class)
public class RocketMqConfiguration {

    private Map<String,Object> producerMap=new HashMap<>();

    @Bean
    public RocketConsumerContainer rocketConsumerContainer(RocketMqProperties mqProperties){
        return new RocketConsumerContainer(mqProperties);
    }
    @Bean
    public RocketProducerContainer rocketProducerContainer(RocketMqProperties mqProperties){
        return new RocketProducerContainer(producerMap,mqProperties);
    }

    @Bean
    @Scope("prototype")
    public RocketAspect rocketAspect(){
        return new RocketAspect(producerMap);
    }
}
