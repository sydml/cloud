package com.sydml.config;


import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Liuym
 * @date 2019/6/26 0026
 */
public class RedisConnections {
    private static final Map<Integer, StatefulRedisConnection<String, String>> REDIS_CONNECTION_MAP = new ConcurrentHashMap<>();

    public static StatefulRedisConnection<String, String> getConnection(int database) {
        return REDIS_CONNECTION_MAP.computeIfAbsent(database, RedisConnections::initConnection);
    }

    private static StatefulRedisConnection<String, String> initConnection(Integer integer) {

        RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379/" + integer);
        return redisClient.connect();
    }

    public static StatefulRedisConnection<String, String> businessConnection() {
        return getConnection(2);
    }

    public static StatefulRedisConnection<String, String> lockConnection() {
        return getConnection(5);
    }

    public static StatefulRedisConnection<String, String> streamConnection() {
        return getConnection(6);
    }

    public static RedisClusterClient getClusterClient() {
        ArrayList<RedisURI> list = new ArrayList<>();
        list.add(RedisURI.create("redis://192.168.207.88:7001"));
        list.add(RedisURI.create("redis://192.168.207.88:7002"));
        list.add(RedisURI.create("redis://192.168.207.88:7003"));
        list.add(RedisURI.create("redis://192.168.207.88:7004"));
        list.add(RedisURI.create("redis://192.168.207.88:7005"));
        list.add(RedisURI.create("redis://192.168.207.88:7006"));
        RedisClusterClient client = RedisClusterClient.create(list);
        return client;

        /*RedisURI redisURI = RedisURI.builder().withHost("192.168.207.88").withPort(7001)
                .withHost("192.168.207.88").withPort(7002)
                .withHost("192.168.207.88").withPort(7003)
                .withHost("192.168.207.88").withPort(7004)
                .withHost("192.168.207.88").withPort(7005)
                .withHost("192.168.207.88").withPort(7006).build();
        RedisClusterClient redisClusterClient = RedisClusterClient.create(redisURI);
        return redisClusterClient;*/
    }

    public static void main(String[] args) {
        /*StatefulRedisConnection<String, String> connection = streamConnection();
        RedisCommands<String, String> redisCommands = connection.sync();
        String messageId = redisCommands.xadd("produce", Collections.singletonMap("key1", "value1"));
        String messageId1 = redisCommands.xadd("produce","myStream-test","myStream-test2");
        String messageId2 = redisCommands.xadd("produce", Collections.singletonMap("key2", "value2"));
        String messageId3 = redisCommands.xadd("produce", Collections.singletonMap("key3", "value3"));
        String messageId4 = redisCommands.xadd("produce", Collections.singletonMap("key4", "value4"));
        List<StreamMessage<String, String>> messages = redisCommands.xrange("produce", Range.create("-", "+"));
        redisCommands.xread(XReadArgs.Builder.block(Duration.ofSeconds(1)),XReadArgs.StreamOffset.from("produce","0"));*/
        RedisClusterClient clusterClient = getClusterClient();
        clusterClient.connect().sync().set("clusterKey", "123");
        System.out.println();
    }

    private static void otherTest(RedisCommands<String, String> redisCommands, String messageId) {
        // Read a message
        List<StreamMessage<String, String>> messages = redisCommands.xread(XReadArgs.StreamOffset.from("produce", messageId));
        for (StreamMessage<String, String> message : messages) {
            System.out.println(message);
        }

        redisCommands.xadd("produce", Collections.singletonMap("key", "value"));

        // Blocking read
        redisCommands.xadd("produce", Collections.singletonMap("key", "value"));
        List<StreamMessage<String, String>> messages2 = redisCommands.xread(XReadArgs.Builder.block(Duration.ofSeconds(2)), XReadArgs.StreamOffset.latest("produce"));
        for (StreamMessage<String, String> message : messages2) {
            System.out.println(message);
        }
        System.out.println();
    }
}
