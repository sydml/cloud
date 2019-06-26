package com.sydml.config;


import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

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

        RedisClient redisClient = RedisClient.create("redis://localhost:6379/" + integer);
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
}
