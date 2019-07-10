package com.sydml.stream;

import com.sydml.config.RedisConnections;
import com.sydml.domain.RedisMessage;
import io.lettuce.core.Range;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liuym
 * @date 2019/7/10 0010
 */
public class StreamMessageImpl implements IStreamMessage{

    @Override
    public List<RedisMessage> getMessages(String name) {
        List<RedisMessage> result = new ArrayList<>();
        List<StreamMessage<String, String>> messages = getRedisMessagesByXRead(name);
        for (StreamMessage<String, String> message : messages) {
            RedisMessage redisMessage = new RedisMessage();
            redisMessage.setId(message.getId());
            redisMessage.setQueue(name);
            redisMessage.setStreamContent(message.getBody());
            result.add(redisMessage);
        }
        return result;
    }

    private List<StreamMessage<String, String>> getRedisMessageByXRange(String name) {
        List<RedisMessage> result = new ArrayList<>();
        RedisCommands<String, String> redisCommands = RedisConnections.streamConnection().sync();
        return redisCommands.xrange("my-stream", Range.create("-", "+"));

    }

    private List<StreamMessage<String, String>> getRedisMessagesByXRead(String name) {
        RedisCommands<String, String> redisCommands = RedisConnections.streamConnection().sync();
        return redisCommands.xread(XReadArgs.Builder.block(Duration.ofSeconds(1)), XReadArgs.StreamOffset.from(name, "0"));
    }
}
