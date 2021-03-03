package com.wjh.common.logs.version;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class WjhCommonLogsV1Application {

    public static void main(String[] args) {
        SpringApplication.run(WjhCommonLogsV1Application.class, args);
    }

}
