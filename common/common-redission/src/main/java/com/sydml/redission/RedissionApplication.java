package com.sydml.redission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Liuym
 * @date 2019/5/16 0016
 */
@SpringBootApplication(scanBasePackages = "com.sydml.redission")
public class RedissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedissionApplication.class, args);
    }
}