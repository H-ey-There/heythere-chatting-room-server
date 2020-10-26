package com.demo.chatdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ChatdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatdemoApplication.class, args);
    }
}
