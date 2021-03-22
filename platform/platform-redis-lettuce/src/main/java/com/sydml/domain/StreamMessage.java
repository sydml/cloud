package com.sydml.domain;

/**
 * @author Liuym
 * @date 2019/7/10 0010
 */
public class StreamMessage {
    private String id;
    private String content;

    public StreamMessage() {
    }

    public StreamMessage(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
