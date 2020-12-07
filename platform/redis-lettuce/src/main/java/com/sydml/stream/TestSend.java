package com.sydml.stream;

import com.sydml.common.utils.StreamUtil;
import com.sydml.config.RedisConnections;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.sync.RedisCommands;


/**
 * @author Liuym
 * @date 2019/7/15 0015
 */
public class TestSend {
    private static RedisCommands<String, String> redisCommands = RedisConnections.streamConnection().sync();
    public static void main(String[] args) {
//        RedisConnections.streamConnection().sync().xadd("fromTail", "test1", "value");

       /* while (true) {

            System.out.println();
        }*/

//        List<StreamMessage<String, String>> messages = redisCommands.xreadgroup(Consumer.from("fromTailGroup", "c2"), XReadArgs.Builder.noack().block(Duration.ofSeconds(2)), XReadArgs.StreamOffset.from("fromTail",">"));
//        System.out.println("thread size :"+messages.size());
        Object lock = redisCommands.eval(StreamUtil.getString(Thread.currentThread().getContextClassLoader().getResourceAsStream("script/lettuceReentrantLock.lua")), ScriptOutputType.INTEGER, new String[]{"lettuceLockName", "lockKey"}, "36000000");
        System.out.println();
        Object unlock = redisCommands.eval(StreamUtil.getString(Thread.currentThread().getContextClassLoader().getResourceAsStream("script/lettuceReentrantUnLock.lua")), ScriptOutputType.INTEGER, new String[]{"lettuceLockName", "lockKey"}, "36000000");
        System.out.println();
    }
}
