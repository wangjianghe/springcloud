package com.wjh.example.admin;

import com.wjh.common.logs.annotation.EnableTrackableLogger;
import com.wjh.common.swagger.config.SwaggerAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringCloudApplication
@EnableTrackableLogger
public class ExampleAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleAdminApplication.class, args);
    }

}
