package com.sydml.stream;

import com.sydml.domain.RedisMessage;

import java.util.List;

/**
 * @author Liuym
 * @date 2019/7/10 0010
 */
public interface IStreamMessage {
    List<RedisMessage> getMessages(String name);
}
