package com.sydml.redis.queue;

import com.sydml.redis.basebean.RedisMessage;
import com.sydml.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;
import java.util.UUID;

/**
 * redis 延迟消息队列 实现
 * Created by Yuming-Liu
 * 日期： 2019-03-09
 * 时间： 16:19
 */
@Component
public class RedisDelayQueue<T> implements IRedisDelayQueue<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDelayQueue.class);

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void sendMessage(String queueKey, T msg) {
        RedisMessage<T> message = new RedisMessage<>();
        String id = queueKey + ":" + UUID.randomUUID().toString();
        message.setId(id);
        message.setContent(msg);
        String s = JsonUtil.encodeJson(message);
        Jedis jedis = jedisPool.getResource();
        try {
            //放入延迟队列,5s后再重试
            jedis.zadd(id, System.currentTimeMillis() + 5000, s);
        } catch (Exception e) {
            LOGGER.warn("redis.delay.queue.send.message.error");
        } finally {
            jedis.close();
        }
    }

    @Override
    public RedisMessage<T> getMessage(String queueKey) {
        RedisMessage<T> message = new RedisMessage<>();
        Jedis jedis = null;
        while (!Thread.interrupted()) {
            try {
                jedis = jedisPool.getResource();
                Set<String> values = jedis.zrangeByScore(queueKey, 0, System.currentTimeMillis(), 0, 1);
                // 如果未获取到消息sleep一会儿再continue尝试
                if (values.isEmpty()) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        LOGGER.warn("redis.delay.queue.getMessage.sleep.error");
                    }
                    continue;
                }
                String s = values.iterator().next();
                /**
                 * todo 考虑redis消息的高可用  remove 队列的消息之前,应该考虑消息是否被正确消费
                 * 1.此处应保证消息被取出消费时,传入消费的回调函数
                 * 2.还应该对取出的消息存库
                 * 3.对于消费失败的消息应该重新入队
                 * 4.对于强制要求顺序问题的此处本就使用redis zset 有序集合,所以应保证业务上如果本条消息失败下面的全部阻塞或者重试获取消息或者抛出异常,下次根据出错的信息查出score处的消息继续手动重发
                 *
                 */

                if (jedis.zrem(queueKey, s) > 0) {
                    message = JsonUtil.decodeJson(s, RedisMessage.class);
                }
            } catch (Exception e) {
                LOGGER.error("redis.delay.queue.getMessage.error");
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
        return message;
    }

    /**
     * 模拟业务代码处理消息
     *
     * @param msg
     */
    public void handleMsg(T msg) {
        System.out.println(msg);
    }

    /**
     * 简单的生产者消费者测试该消息队列
     *
     * @param queueKey
     */
    public void testRedisQueue(String queueKey) {
        Thread producer = new Thread((() -> {
            for (int i = 0; i < 10; i++) {
                sendMessage(queueKey, (T) ("codehole" + i));
            }
        }));

        Thread consumer = new Thread(() -> {
            RedisMessage<T> message = getMessage(queueKey);
            handleMsg(message.getContent());
        });

        producer.start();
        consumer.start();
        try {
            producer.join();
            Thread.sleep(10);
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {

        }
    }
}
