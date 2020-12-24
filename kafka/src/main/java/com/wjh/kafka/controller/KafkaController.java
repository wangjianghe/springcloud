package com.wjh.kafka.controller;


import com.wjh.kafka.entity.IMyTestKafkaOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class KafkaController {
    @Autowired
    IMyTestKafkaOut iMyTestKafkaOut;

    @RequestMapping("/testStream")
    public String testStream(){
        iMyTestKafkaOut.output().send(
            MessageBuilder.withPayload("发送消息；"+new Date()).build()
        );
        return "success";
    }
}
