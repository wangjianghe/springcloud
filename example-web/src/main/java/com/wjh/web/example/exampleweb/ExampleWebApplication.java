package com.wjh.web.example.exampleweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.wjh"})
@EnableFeignClients(basePackages = "com.wjh")
@SpringCloudApplication
public class ExampleWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleWebApplication.class, args);
    }

}
