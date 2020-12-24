package com.wjh.kafkaconsumer.service;

import com.wjh.kafkaconsumer.entity.IMyTestKafkaInput;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    @StreamListener(IMyTestKafkaInput.MY_TEST_KAFKA_INPUT)
    public void getMessage(String messageBody){
        System.out.println(messageBody);
    }
}
