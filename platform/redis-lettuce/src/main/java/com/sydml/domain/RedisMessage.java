package com.sydml.domain;

import java.util.Map;

/**
 * @author Liuym
 * @date 2019/6/26 0026
 */
public class RedisMessage {

    private String id;

    private String queue;

    private String context;

    private Map<String, String> streamContent;

    public RedisMessage() {
    }

    public RedisMessage(String id, String queue, String context) {
        this.id = id;
        this.queue = queue;
        this.context = context;
    }

    public RedisMessage(String id, String context) {
        this.id = id;
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Map<String, String> getStreamContent() {
        return streamContent;
    }

    public void setStreamContent(Map<String, String> streamContent) {
        this.streamContent = streamContent;
    }
}
