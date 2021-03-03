package com.wjh.wjhcom.common.mq.rocket.aspect;

import com.alibaba.fastjson.JSON;
import com.wjh.wjhcom.common.mq.rocket.annotation.CommonMessage;
import com.wjh.wjhcom.common.mq.rocket.annotation.RocketMessage;
import com.wjh.wjhcom.common.mq.rocket.utils.AspectUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Map;

@Aspect
@Slf4j
@Data
public class RocketAspect {
    private Map<String,Object> consumerMap;

    public RocketAspect(Map<String, Object> consumerMap) {
        this.consumerMap = consumerMap;
    }

    @Pointcut("@annotation(com.wjh.wjhcom.common.mq.rocket.annotation.CommonMessage)")
    public void commonMessagePointcut(){log.info("start sending commonMessage");}
    @Around("commonMessagePointcut()")
    public Object commonMessageAround(ProceedingJoinPoint point) throws Throwable {
        RocketMessage rocketMessage=AspectUtils.getDeclaringClassAnnotation(point, RocketMessage.class);
        CommonMessage message=AspectUtils.getAnnotation(point, CommonMessage.class);
        Object o=point.proceed();
        String producerKey=rocketMessage.groupId() +
                message.topic() +
                message.tag();
        DefaultMQProducer producer= (DefaultMQProducer) consumerMap.get(producerKey);
        String objString=JSON.toJSONString(o);
        Message sendMessage=new Message(message.topic(),message.tag(),objString.getBytes());
        producer.send(sendMessage);
        return o;
    }
}
