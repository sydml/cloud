package com.sydml.etcd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author liuyuming
 * @date 2021-03-26 17:35:45
 */
@SpringBootApplication
@EnableEurekaServer
public class EtcdApplication {
    public static void main(String[] args) {
        SpringApplication.run(EtcdApplication.class, args);
    }
}
