package com.sydml.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

/**
 * @author Liuym
 * @date 2019/7/2 0002
 */
@Component
@EnableScheduling
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 定时任务
     */
    @Scheduled(cron = "00/1 * * * * ?")
    public void send(){
        String message = UUID.randomUUID().toString();
        ListenableFuture<?> future = kafkaTemplate.send("test003","kafka message test");
        String message2 = "第二种类的订阅消息发送";
        ListenableFuture<?> future2 = kafkaTemplate.send("test002",message2);
        future.addCallback(o -> System.out.println("send-消息发送成功：" + message), throwable -> System.out.println("消息发送失败：" + message));
        future2.addCallback(o -> System.out.println("send-消息发送成功：" + message2), throwable -> System.out.println("消息发送失败：" + message2));
    }
}
