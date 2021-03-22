package com.sydml.test;

import com.sydml.constant.QueueName;
import com.sydml.domain.RedisMessage;
import com.sydml.impl.MessageService;
import com.sydml.listener.Listener;

/**
 * @author Liuym
 * @date 2019/6/26 0026
 */
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.process();
    }

    private void process() {
        MessageService messageService = new MessageService();
        RedisMessage message = new RedisMessage();
        message.setQueue(QueueName.TEST_QUEUE);
        message.setContext("test");
        messageService.sendMessage(message);
        Listener listener = new Listener.RedisBuilder().queue(QueueName.TEST_QUEUE).consumer(this::print).builder();
        messageService.listen(listener);
    }

    public void print(RedisMessage redisMessage) {
        System.out.println(redisMessage.getContext());
    }

}
