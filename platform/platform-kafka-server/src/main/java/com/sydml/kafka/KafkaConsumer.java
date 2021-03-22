package com.sydml.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Liuym
 * @date 2019/7/2 0002
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"test001"})
    public void receive(String message){
        System.out.println("test001--消费消息:" + message);
    }

    @KafkaListener(topics = {"test002"})
    public void receive2(String message){
        System.out.println("test002--消费消息:" + message);
    }
}
