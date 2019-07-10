package com.sydml.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Liuym
 * @date 2019/7/2 0002
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"app_log"})
    public void receive(String message){
        System.out.println("app_log--消费消息:" + message);
    }

    @KafkaListener(topics = {"app_log2"})
    public void receive2(String message){
        System.out.println("app_log2--消费消息:" + message);
    }
}
