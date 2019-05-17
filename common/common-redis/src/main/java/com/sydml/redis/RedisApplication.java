package com.sydml.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Liuym
 * @date 2019/5/16 0016
 */
@SpringBootApplication(scanBasePackages = "com.sydml.redis")
public class RedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }
}
