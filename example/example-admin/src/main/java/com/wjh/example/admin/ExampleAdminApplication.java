package com.wjh.example.admin;

import com.wjh.common.swagger.config.SwaggerAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan(basePackages = {"com.wjh"})
@EnableFeignClients(basePackages = "com.wjh")
@SpringCloudApplication
public class ExampleAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExampleAdminApplication.class, args);
    }

}
