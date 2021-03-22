package com.sydml.test;

import com.sydml.common.utils.StreamUtil;
import com.sydml.config.RedisConnections;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.sync.RedisCommands;

/**
 * @author liuyuming
 * @date 2020-12-07 15:51:33
 */
public class LockTest {
    private static RedisCommands<String, String> redisCommands = RedisConnections.streamConnection().sync();
    public static void main(String[] args) {
        Object lock = redisCommands.eval(StreamUtil.getString(Thread.currentThread().getContextClassLoader().getResourceAsStream("script/lettuceReentrantLock.lua")), ScriptOutputType.INTEGER, new String[]{"lettuceLockName", "lockKeyTest"}, "36000000");
        System.out.println();
        Object unlock = redisCommands.eval(StreamUtil.getString(Thread.currentThread().getContextClassLoader().getResourceAsStream("script/lettuceReentrantUnLock.lua")), ScriptOutputType.INTEGER, new String[]{"lettuceLockName", "lockKey"}, "36000000");
        System.out.println();
    }
}
