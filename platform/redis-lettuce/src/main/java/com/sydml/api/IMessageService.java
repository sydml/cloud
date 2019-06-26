package com.sydml.api;

import com.sydml.domain.RedisMessage;
import com.sydml.listener.Listener;

/**
 * @author Liuym
 * @date 2019/6/26 0026
 */
public interface IMessageService {
    void sendMessage(RedisMessage redisMessage);

    void sendDelayMessage(RedisMessage redisMessage);

    void sendMessage(String queue, RedisMessage redisMessage);

    void listen(Listener listener);
}
