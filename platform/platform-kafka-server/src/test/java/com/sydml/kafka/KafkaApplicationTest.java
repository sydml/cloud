package com.sydml.kafka;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

/**
 * @author liuyuming
 * @date 2021-03-22 18:27:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class KafkaApplicationTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Test
    public void testProducer(){
        String message = UUID.randomUUID().toString();
        ListenableFuture<?> future = kafkaTemplate.send("test001","kafka message test");
        String message2 = "第二种类的订阅消息发送";
        ListenableFuture<?> future2 = kafkaTemplate.send("test001",message2);
        future.addCallback(o -> System.out.println("send-消息发送成功：" + message), throwable -> System.out.println("消息发送失败：" + message));
        future2.addCallback(o -> System.out.println("send-消息发送成功：" + message2), throwable -> System.out.println("消息发送失败：" + message2));
    }

}