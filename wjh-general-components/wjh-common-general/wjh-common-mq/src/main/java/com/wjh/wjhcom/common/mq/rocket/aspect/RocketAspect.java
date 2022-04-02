package com.wjh.wjhcom.common.mq.rocket.aspect;

import com.alibaba.fastjson.JSON;
import com.wjh.wjhcom.common.mq.rocket.annotation.CommonMessage;
import com.wjh.wjhcom.common.mq.rocket.annotation.RocketMessage;
import com.wjh.wjhcom.common.mq.rocket.annotation.TransactionMessage;
import com.wjh.wjhcom.common.mq.rocket.callback.DefaultSendCallback;
import com.wjh.wjhcom.common.mq.rocket.constants.MessageSendType;
import com.wjh.wjhcom.common.mq.rocket.utils.AspectUtils;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

@Aspect
@Slf4j
@Data
public class RocketAspect implements ApplicationContextAware {
    private Map<String,Object> consumerMap;
    private ApplicationContext applicationContext;

    public RocketAspect(Map<String, Object> consumerMap) {
        this.consumerMap = consumerMap;
    }

    @Pointcut("@annotation(com.wjh.wjhcom.common.mq.rocket.annotation.CommonMessage)")
    public void commonMessagePointcut(){log.info("start sending commonMessage");}
    @Pointcut("@annotation(com.wjh.wjhcom.common.mq.rocket.annotation.TransactionMessage)")
    public void transactionMessagePointcut(){
        log.info("start sending transactionMessage");
    }
    @Around("commonMessagePointcut()")
    public Object commonMessageAround(ProceedingJoinPoint point) throws Throwable {
        log.info("start");
        RocketMessage rocketMessage=AspectUtils.getDeclaringClassAnnotation(point, RocketMessage.class);
        CommonMessage message=AspectUtils.getAnnotation(point, CommonMessage.class);
        Object o=point.proceed();
        String producerKey=rocketMessage.groupId() +
                message.topic() +
                message.tag();
        DefaultMQProducer producer= (DefaultMQProducer) consumerMap.get(producerKey);
        if (producer==null){
            throw new Exception("dont found producer:"+producerKey);
        }
        sendCommonMessage(message,producer,o);
        return o;
    }
    @Around("transactionMessagePointcut()")
    public Object transactionMessageAround(ProceedingJoinPoint point) throws Throwable {
        RocketMessage rocketMessage=AspectUtils.getDeclaringClassAnnotation(point,RocketMessage.class);
        TransactionMessage message=AspectUtils.getAnnotation(point,TransactionMessage.class);
        Object o=point.proceed();
        String producerKey=rocketMessage.groupId()+message.topic()+message.tag();
        Object productObj=consumerMap.get(producerKey);
        if (productObj.getClass()!=TransactionMQProducer.class){
            throw new Exception("rocketMessage messageType is error,this is transactionMessage");
        }
        TransactionMQProducer producer= (TransactionMQProducer) productObj;
        sendTransactionMessage(message,producer,o);
        return o;
    }
    public void sendCommonMessage(CommonMessage message,DefaultMQProducer producer,Object o) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String objString=JSON.toJSONString(o);
        Message sendMessage=new Message(message.topic(),message.tag(),objString.getBytes());
        if (MessageSendType.SEND.equals(message.messageSendType())){
            producer.send(sendMessage);
        }else if (MessageSendType.SEND_ASYNC.equals(message.messageSendType())){
            SendCallback callback=getCallBack(applicationContext,message.callback());
            producer.send(sendMessage,callback);
        }else if (MessageSendType.SEND_ONE_WAY.equals(message.messageSendType())){
            producer.sendOneway(sendMessage);
        }
        producer.send(sendMessage);
    }
    public void sendTransactionMessage(TransactionMessage transactionMessage,TransactionMQProducer producer,Object o) throws IllegalAccessException, InstantiationException, MQClientException {
        String objString=JSON.toJSONString(o);
        Message sendMessage=new Message(transactionMessage.topic(),transactionMessage.tag(),objString.getBytes());
        LocalTransactionExecuter executor=transactionMessage.executer().newInstance();
        TransactionSendResult result=producer.sendMessageInTransaction(sendMessage,executor,transactionMessage.arg());
        log.info("sendTransactionResult:"+result);
    }

    public static SendCallback getCallBack(ApplicationContext context,Class<? extends SendCallback> callBack){
        SendCallback sendCallback;
        if (DefaultSendCallback.class.equals(callBack)){
            sendCallback=new DefaultSendCallback();
        }else{
            sendCallback=context.getBean(callBack);
        }
        return sendCallback;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
