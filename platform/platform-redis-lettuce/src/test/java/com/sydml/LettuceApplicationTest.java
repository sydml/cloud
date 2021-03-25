package com.sydml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

/**
 * @author liuyuming
 * @date 2021-03-25 14:38:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LettuceApplicationTest {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test(){
        boolean lock = lock("script/ReentrantLock.lua", "myLock", "user002", 3600000);
        System.out.println();
    }
    private boolean lock(String luaPath,String lockName,String key,long time) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource(luaPath)));
        return redisTemplate.execute(script, Collections.singletonList(lockName), key, time) == null;
    }
}