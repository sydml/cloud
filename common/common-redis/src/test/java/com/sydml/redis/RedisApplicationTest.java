package com.sydml.redis;

import com.sydml.common.utils.StreamUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisApplicationTest {
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private Jedis jedis;
    @Test
    public void test(){
        System.out.println("test...");
    }

    @Test
    public void testRedis(){
        if (lock("script/ReentrantLock.lua", "myLock","user002", 3600000)) {

        }
        System.out.println();
    }

    private boolean lock(String luaPath,String lockName,String key,long time) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource(luaPath)));
        return redisTemplate.execute(script, Collections.singletonList(lockName), key, time) == null;
    }

    @Test
    public void testLock(){
        Object eval = jedis.eval(StreamUtil.getString(Thread.currentThread().getContextClassLoader().getResourceAsStream("script/jedisReentrantLock.lua")), 2, "jedisLockKey", "lockNameTest","3600000000");
        System.out.println();
    }

}