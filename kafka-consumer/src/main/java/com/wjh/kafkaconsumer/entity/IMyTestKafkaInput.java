package com.wjh.kafkaconsumer.entity;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface IMyTestKafkaInput {
    String MY_TEST_KAFKA_INPUT="myTestKafkaInput";
    @Input(MY_TEST_KAFKA_INPUT)
    SubscribableChannel input();
}
