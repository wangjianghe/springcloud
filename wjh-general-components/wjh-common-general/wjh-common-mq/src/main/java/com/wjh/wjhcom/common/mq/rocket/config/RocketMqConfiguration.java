package com.wjh.wjhcom.common.mq.rocket.config;


import com.wjh.wjhcom.common.mq.rocket.annotation.EnableWJHRocketMQ;
import com.wjh.wjhcom.common.mq.rocket.aspect.RocketAspect;
import com.wjh.wjhcom.common.mq.rocket.container.RocketConsumerContainer;
import com.wjh.wjhcom.common.mq.rocket.container.RocketProducerContainer;
import com.wjh.wjhcom.common.mq.rocket.init.RocketMqInitLoadListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(RocketMqProperties.class)
@ConditionalOnBean(annotation = {EnableWJHRocketMQ.class})
@Slf4j
public class RocketMqConfiguration {

    private Map<String,Object> producerMap=new HashMap<>();

    @Bean
    public RocketConsumerContainer rocketConsumerContainer(RocketMqProperties mqProperties){
        log.info("wjh-RocketConsumerContainer start");
        return new RocketConsumerContainer(mqProperties);
    }
    @Bean
    public RocketProducerContainer rocketProducerContainer(RocketMqProperties mqProperties){
        log.info("wjh-rocketProducerContainer start");
        return new RocketProducerContainer(producerMap,mqProperties);
    }

    @Bean
    @Scope("prototype")
    public RocketAspect rocketAspect(){
        log.info("build rocketAspect");
        return new RocketAspect(producerMap);
    }
}
