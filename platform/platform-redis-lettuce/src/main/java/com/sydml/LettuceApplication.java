package com.sydml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author liuyuming
 * @date 2021-03-22 18:13:38
 */
@SpringBootApplication
@EnableEurekaServer
public class LettuceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LettuceApplication.class, args);
    }

}
