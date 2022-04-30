package com.wjh.wjhcom.common.mq.rocket.container;

import com.wjh.wjhcom.common.mq.rocket.annotation.CommonMessage;
import com.wjh.wjhcom.common.mq.rocket.annotation.RocketMessage;
import com.wjh.wjhcom.common.mq.rocket.annotation.TestMessage;
import com.wjh.wjhcom.common.mq.rocket.annotation.TransactionMessage;
import com.wjh.wjhcom.common.mq.rocket.config.RocketMqProperties;
import com.wjh.wjhcom.common.mq.rocket.core.factory.MqClientFactory;
import com.wjh.wjhcom.common.mq.rocket.core.factory.RocketMqClientFactory;
import com.wjh.wjhcom.common.mq.rocket.utils.AnnotatedMethodUtils;
import com.wjh.wjhcom.common.mq.rocket.utils.AopTargetUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class RocketProducerContainer implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private Map<String,Object> producerContainer;
    private RocketMqProperties rocketProperties;
    private Properties properties=new Properties();
    private Map<String,Object> beanMap=new HashMap<>();

    public RocketProducerContainer(Map<String, Object> producerContainer, RocketMqProperties rocketProperties) {
        this.producerContainer = producerContainer;
        this.rocketProperties = rocketProperties;
    }

    @PostConstruct
    public void initProducerContainer() throws Exception {
        beanMap=this.applicationContext.getBeansWithAnnotation(RocketMessage.class);
        if (beanMap.size() == 0){
            return;
        }
        for (Object bean:beanMap.values()){
            RocketMessage rocketMessage= bean.getClass().getAnnotation(RocketMessage.class);
            Object targetBean= AopTargetUtils.getTarget(bean);
            Method[] methods=targetBean.getClass().getDeclaredMethods();
            for (Method method:methods){
                CommonMessage commonMessage;
                TransactionMessage transactionMessage;
                commonMessage=AnnotationUtils.findAnnotation(method,CommonMessage.class);
                transactionMessage=AnnotationUtils.findAnnotation(method,TransactionMessage.class);
                if (commonMessage!=null&&transactionMessage!=null){
                    log.error("commonMessage和transactionMessage不能同时存在");
                }else if(commonMessage!=null){
                    Object producer=createProducer(bean,rocketProperties);
                    String producerKey=rocketMessage.groupId() +
                            commonMessage.topic() +
                            commonMessage.tag();
                    log.info("product-info-{}",producerKey);
                    producerContainer.put(producerKey,producer);
                }else if (transactionMessage!=null){
                    Object producer=createProducer(bean,rocketProperties);
                    String producerKey=rocketMessage.groupId() +
                            transactionMessage.topic() +
                            transactionMessage.tag();
                    log.info("transaction-product-info-{}",producerKey);
                    producerContainer.put(producerKey,producer);
                }
            }
        }
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    private Object createProducer(Object bean,RocketMqProperties rocketMqProperties) throws Exception {
        MqClientFactory factory=new RocketMqClientFactory();
        String address=rocketMqProperties.getAddress();
        if (StringUtils.isEmpty(address)){
            throw new Exception("address is null");
        }
        properties.setProperty("address",address);
        return factory.createProducer(properties,bean);
    }
}
