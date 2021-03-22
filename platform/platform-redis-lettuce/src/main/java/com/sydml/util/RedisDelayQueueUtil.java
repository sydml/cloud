package com.sydml.util;

import com.sydml.config.RedisConnections;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

/**
 * @author Liuym
 * @date 2019/6/26 0026
 */
public class RedisDelayQueueUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDelayQueueUtil.class);

    private static final int MAX_RETRY_NUMBER = 5;

    public static void publish(String queue, String message, Long milliseconds) {
        send(queue, message, milliseconds);
    }

    private static void send(String queue, String message, Long milliseconds) {
        long score = Calendar.getInstance().getTime().getTime() + milliseconds;
        try {
            doSendDelay(queue, message, score);
        } catch (Exception e) {
            retry(queue, message, milliseconds);
        }
    }

    private static void doSendDelay(String queue, String message, Long score) {
        RedisCommands<String, String> redisCommands = RedisConnections.streamConnection().sync();
        redisCommands.zadd(queue, score, message);
    }

    private static void retry(String queue, String message, Long score) {
        int retryCount = 0;
        while (retryCount < MAX_RETRY_NUMBER) {
            try {
                doSendDelay(queue, message, score);
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
