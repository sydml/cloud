package com.sydml.domain;

/**
 * @author Liuym
 * @date 2019/6/26 0026
 */
public class RedisMessage {
    private String queue;

    private String context;

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
