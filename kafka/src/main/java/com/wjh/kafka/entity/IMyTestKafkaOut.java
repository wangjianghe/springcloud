package com.wjh.kafka.entity;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface IMyTestKafkaOut {
    String MY_TEST_KAFKA_OUT="myTestKafkaOut";
    @Output(MY_TEST_KAFKA_OUT)
    MessageChannel output();
}
