package com.sydml.redis.controller;

import com.sydml.common.utils.TimeUtil;
import com.sydml.redis.queue.RedisDelayQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-07
 * 时间： 22:58
 */
@RestController
@RequestMapping("/test")
public class RedisTestController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisDelayQueue redisDelayQueue;

    @Autowired
    private Jedis jedis;

    @GetMapping
    public void test() {
//        jedis.set("test", "value");
        redisDelayQueue.testRedisQueue("queue-demo");
    }


    /*@GetMapping("/send-message")
    public void sendMessage() {
        redisDelayQueue.sendMessage("message", "test");
    }*/

    @GetMapping("/batch-send-message")
    public void batchSendMessage() {
        for (int i = 0; i < 10; i++) {
            redisDelayQueue.sendMessage("message", "test" + i);
        }
    }

    @GetMapping("/send-message")
    public String getMessage() {
       return redisDelayQueue.getMessage("message").toString();
    }

    @GetMapping("/set-with-timeout")
    public void testSetWithTimeout(String redisKey,long timeOut) {
//        jedis.set("test", "value");
        jedis.set(redisKey, "testValue", "nx", "ex", 5);
    }

}
