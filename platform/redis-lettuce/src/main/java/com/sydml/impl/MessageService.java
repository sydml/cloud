package com.sydml.impl;

import com.sydml.api.IMessageService;
import com.sydml.common.utils.JsonUtil;
import com.sydml.constant.QueueName;
import com.sydml.domain.RedisMessage;
import com.sydml.listener.Listener;
import com.sydml.util.RedisQueueUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Liuym
 * @date 2019/6/26 0026
 */
public class MessageService implements IMessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    private static final int SLEEP_TIME = 5 * 1000;

    @Override
    public void sendMessage(RedisMessage redisMessage) {
        String message = JsonUtil.encodeJson(redisMessage);
        RedisQueueUtil.push(QueueName.TEST_QUEUE, message);
    }

    @Override
    public void sendDelayMessage(RedisMessage redisMessage) {
        String message = JsonUtil.encodeJson(redisMessage);
        RedisQueueUtil.push(QueueName.DELAY_TEST_QUEUE, message);
    }

    @Override
    public void sendMessage(String queue, RedisMessage redisMessage) {
        String message = JsonUtil.encodeJson(redisMessage);
        RedisQueueUtil.push(queue, message);
    }

    @Override
    public void listen(Listener listener) {
        String queue = listener.getQueue();
        if (StringUtils.isNotEmpty(listener.getQueue())) {
            Thread thread = new Thread(() -> listenQueue(listener), "redis-listener: " + queue);
            thread.start();
            return;
        }
        throw new IllegalArgumentException();
    }

    private void listenQueue(Listener listener) {
        String queue = listener.getQueue();
        while (true) {
            try {
                String message = RedisQueueUtil.pop(queue);
                if (StringUtils.isEmpty(message)) {
                    Thread.sleep(SLEEP_TIME);
                    continue;
                }
                RedisMessage redisMessage = JsonUtil.decodeJson(message, RedisMessage.class);
                listener.getMessageConsumer().accept(redisMessage);
            } catch (Exception e) {
                LOGGER.error("RedisMessageQueueService.listenQueue.error", e);
            }
        }
    }
}
