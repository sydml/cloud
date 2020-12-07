package com.sydml.redission;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.RedissonLocalCachedMap;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedissionApplicationTest {
    @Autowired
    private RedissonClient redissonClient;
    @Test
    public void test(){
        RLock lock = redissonClient.getLock("testKey");
        lock.lock(3600, TimeUnit.SECONDS);
        System.out.println("lock1 lock");
        new Thread(new Runnable() {
            @Override
            public void run() {
                RLock lock2 = redissonClient.getLock("testKey");
                lock2.lock(3600, TimeUnit.SECONDS);
                System.out.println("lock2 lock");
            }
        }).run();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RLock lock3 = redissonClient.getLock("testKey");
                lock3.lock(3600, TimeUnit.SECONDS);
                System.out.println("lock3 lock");
            }
        }).run();

        lock.unlock();

        System.out.println("test...");
    }
}