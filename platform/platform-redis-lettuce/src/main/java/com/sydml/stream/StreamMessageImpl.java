package com.sydml.stream;

import com.sydml.config.RedisConnections;
import com.sydml.domain.RedisMessage;
import io.lettuce.core.Consumer;
import io.lettuce.core.Range;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Liuym
 * @date 2019/7/10 0010
 */
public class StreamMessageImpl implements IStreamMessage {
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

    public void sendMessage(String key, Map<String, String> messages) {
        String xadd = redisCommands.xadd(key, messages);
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

    public void readGroup(String key, String groupName, String consumerName) {
        List<StreamMessage<String, String>> messages = redisCommands.xreadgroup(Consumer.from(groupName, consumerName), XReadArgs.Builder.noack().block(Duration.ofSeconds(2)), XReadArgs.StreamOffset.from(key,">"));
        Long count = 0L;
        for (StreamMessage<String, String> message : messages) {
            String id = message.getId();
            for (Map.Entry<String, String> entry : message.getBody().entrySet()) {
                doBusiness(entry.getValue());
                Long xack = redisCommands.xack(key, groupName, id);
                count ++;
                System.out.println("ack:" + xack);
            }
            System.out.println("已确认消息："+count);
            System.out.println("未确认的消息："+redisCommands.xreadgroup(Consumer.from(groupName, "consumerName"), XReadArgs.Builder.noack(), XReadArgs.StreamOffset.from(key, ">")).size());
        }
    }

    public void groupReadFromHeadAndAck(String key, String groupName, String consumerName) {
        List<StreamMessage<String, String>> messages = redisCommands.xreadgroup(Consumer.from(groupName, "consumerName"),
                XReadArgs.Builder.noack(), XReadArgs.StreamOffset.from(key, ">"));
        System.out.println("main size :"+messages.size());
        for (StreamMessage<String, String> message : messages) {
            String id = message.getId();
            for (Map.Entry<String, String> entry : message.getBody().entrySet()) {
                doBusiness(entry.getValue());
                Long xack = redisCommands.xack(key, groupName, id);
                System.out.println("ack:" + xack);
            }

        }

        System.out.println("剩余消息：" + getCount(key));
        System.out.println("剩余消息：" + getRedisMessagesByXRead(key));
        System.out.println("未确认的消息："+redisCommands.xreadgroup(Consumer.from(groupName, "consumerName"), XReadArgs.Builder.noack(), XReadArgs.StreamOffset.from(key, ">")).size());
    }

    public String createGroup(String groupName, String key, String offset) {
        String xgroupCreate = redisCommands.xgroupCreate(XReadArgs.StreamOffset.from(key, offset), groupName);
        if ("OK".equals(xgroupCreate)) {
            return groupName;
        } else {
            throw new RuntimeException("create createGroupFromHead fail");
        }
    }

    public Boolean destroyGroup(String groupName, String key) {
        return redisCommands.xgroupDestroy(key, groupName);
    }

    private static void doBusiness(String value) {
        System.out.println(value);
    }


    public static void main(String[] args) {
        StreamMessageImpl streamMessage = new StreamMessageImpl();
        streamMessage.sendMessage("fromTail", Collections.singletonMap("test","value"));
        redisCommands.xgroupDestroy("fromTail", "fromTailGroup");
        redisCommands.xgroupCreate(XReadArgs.StreamOffset.from("fromTail","0-0"),"fromTailGroup");
        streamMessage.readGroup("fromTail", "fromTailGroup", "c1");
    }
}
