package com.sydml.redis.basebean;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-09
 * 时间： 20:29
 */
public class RedisMessage<T> {

    private String id;

    private T content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
