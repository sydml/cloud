package com.sydml.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.lang.Nullable;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-07
 * 时间： 21:51
 */
public class RedisTest {
    @Autowired
    private static RedisTemplate redisTemplate;


    public static void main(String[] args) {
        redisTemplate.execute(new SessionCallback() {
            @Nullable
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.opsForHash().putIfAbsent("hash", "test", "value");
                redisOperations.opsForList().rightPush("lsit", "java");
                return null;
            }
        });
    }

}
