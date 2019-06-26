package com.sydml.listener;


import com.sydml.domain.RedisMessage;

import java.util.function.Consumer;
import java.util.function.Function;

public class Listener {

    private String queue;

    private Consumer<RedisMessage> messageConsumer;

    private Function<RedisMessage, RedisMessage> messageFunction;

    private Consumer<RedisMessage> afterAck;

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public Consumer<RedisMessage> getMessageConsumer() {
        return messageConsumer;
    }

    public void setMessageConsumer(Consumer<RedisMessage> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    public Function<RedisMessage, RedisMessage> getMessageFunction() {
        return messageFunction;
    }

    public void setMessageFunction(Function<RedisMessage, RedisMessage> messageFunction) {
        this.messageFunction = messageFunction;
    }

    public Consumer<RedisMessage> getAfterAck() {
        return afterAck;
    }

    public void setAfterAck(Consumer<RedisMessage> afterAck) {
        this.afterAck = afterAck;
    }

    public static class RedisBuilder{

        private String queue;

        private Consumer<RedisMessage> messageConsumer;

        public RedisBuilder queue(String queue) {
            this.queue = queue;
            return this;
        }

        public RedisBuilder consumer(Consumer<RedisMessage> consumer) {
            this.messageConsumer = consumer;
            return this;
        }

        public Listener builder() {
            return new Listener(this);
        }
    }

    public static class StreamBuilder{
        private String queue;

        private Function<RedisMessage, RedisMessage> messageFunction;

        private Consumer<RedisMessage> afterAck;

        public StreamBuilder queue(String queue) {
            this.queue = queue;
            return this;
        }

        public StreamBuilder messageFunction(Function<RedisMessage, RedisMessage> messageFunction) {
            this.messageFunction = messageFunction;
            return this;
        }

        public StreamBuilder afterAck(Consumer<RedisMessage> afterAck) {
            this.afterAck = afterAck;
            return this;
        }
        public Listener builder() {
            return new Listener(this);
        }
    }

    public Listener(RedisBuilder builder) {
        this.queue = builder.queue;
        this.messageConsumer = builder.messageConsumer;
    }

    public Listener(StreamBuilder builder) {
        this.queue = builder.queue;
        this.messageFunction = builder.messageFunction;
        this.afterAck = builder.afterAck;
    }

}
