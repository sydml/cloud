package com.sydml.util;

import com.sydml.config.RedisConnections;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Liuym
 * @date 2019/6/26 0026
 */

public final class RedisQueueUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisQueueUtil.class);

    private static final int MAX_RETRY_NUMBER = 5;
    private static final int TIMEOUT_SECOND = 3;

    public static void push(String message) {
        send("test-queue", message);
    }

    public static void push(String queue, String message) {
        send(queue, message);
    }

    public static String pop(String queue) {
        try {
            RedisCommands<String, String> redisCommands = RedisConnections.lockConnection().sync();
            Long exists = redisCommands.exists(queue);
            if (exists == 0) {
                return null;
            }
            return redisCommands.brpop(TIMEOUT_SECOND, queue).getValue();
        } catch (Exception e) {
            LOGGER.error("RedisQueueMessage subscription message error, queue: " + queue + ", e:", e);
        }
        return null;
    }

    private static void send(String queue, String message) {
        try {
            doSend(queue, message);
        } catch (Exception e) {
            retry(queue, message);
        }
    }

    private static void doSend(String queue, String message) {
        RedisCommands<String, String> redisCommands = RedisConnections.lockConnection().sync();
        redisCommands.lpush(queue, message);
    }

    private static void retry(String queue, String message) {
        int retryCount = 0;
        while (retryCount < MAX_RETRY_NUMBER) {
            try {
                doSend(queue, message);
                break;
            } catch (Exception e) {
                retryCount = retryCount + 1;
                if (retryCount >= MAX_RETRY_NUMBER) {
                    LOGGER.error("RedisQueueMessage publish message error, message: " + message + ", queue: " + queue + ", e:", e);
                }
            }
        }
    }

}

