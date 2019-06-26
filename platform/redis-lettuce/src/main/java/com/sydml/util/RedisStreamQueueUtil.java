package com.sydml.util;

import com.sydml.config.RedisConnections;
import com.sydml.constant.QueueName;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * @author Liuym
 * @date 2019/6/26 0026
 */
public class RedisStreamQueueUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisStreamQueueUtil.class);

    private static final int MAX_RETRY_NUMBER = 5;

    public static void publish(String message) {
            send(QueueName.TEST_QUEUE, message);
    }

    public static String subscription(String queue) {
        try {
            RedisCommands<String, String> redisCommands = RedisConnections.streamConnection().sync();
            List<StreamMessage<String, String>> messages = redisCommands.xread(XReadArgs.Builder.block(Duration.ofSeconds(2)), XReadArgs.StreamOffset.latest(queue));
            for (StreamMessage<String, String> streamMessage : messages) {
                System.out.println(streamMessage);
            }
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
        RedisCommands<String, String> redisCommands = RedisConnections.streamConnection().sync();
        String messageOffset = redisCommands.xadd(queue, message);
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
