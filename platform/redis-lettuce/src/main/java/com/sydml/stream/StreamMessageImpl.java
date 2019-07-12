package com.sydml.stream;

import com.sydml.common.utils.JsonUtil;
import com.sydml.config.RedisConnections;
import com.sydml.domain.RedisMessage;
import io.lettuce.core.Range;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Liuym
 * @date 2019/7/10 0010
 */
public class StreamMessageImpl implements IStreamMessage{
   private static RedisCommands<String, String> redisCommands = RedisConnections.streamConnection().sync();

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
        return redisCommands.xrange(name, Range.create("-", "+"));

    }

    private List<StreamMessage<String, String>> getRedisMessagesByXRead(String name) {
        return redisCommands.xread(XReadArgs.Builder.block(Duration.ofSeconds(1)), XReadArgs.StreamOffset.from(name, "0"));
    }

    public static List<StreamMessage<String, String>> getLimitFromStart(String name, Long count) {
       return redisCommands.xread(XReadArgs.Builder.count(3), XReadArgs.StreamOffset.from("my-stream", count.toString()));
    }

    public static Long getCount(String name) {

        return redisCommands.xlen(name);
    }

    public static void createGroup(String key, String groupName) {
        List<StreamMessage<String, String>> limitFromStart = redisCommands.xread(XReadArgs.Builder.noack().block(Duration.ofSeconds(1)), XReadArgs.StreamOffset.from(key, "0"));
        String xgroupCreate = redisCommands.xgroupCreate(XReadArgs.StreamOffset.from(key, "0"), groupName);
        for (StreamMessage<String, String> message : limitFromStart) {
            String id = message.getId();
            for (Map.Entry<String, String> entry : message.getBody().entrySet()) {
                System.out.println(entry.getValue());
                Long xack = redisCommands.xack(key, xgroupCreate, id);
            }

        }
        System.out.println();


    }

    public static void main(String[] args) {
        StreamMessageImpl streamMessage = new StreamMessageImpl();
        List<RedisMessage> messages = streamMessage.getMessages("my-stream");
        System.out.println(JsonUtil.encodeJson(messages));
        System.out.println(getCount("my-stream"));
        createGroup("my-stream", "groupName");
    }
}
