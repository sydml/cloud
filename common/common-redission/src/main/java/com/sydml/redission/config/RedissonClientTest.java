package com.sydml.redission.config;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author liuyuming
 * @date 2020-07-28 20:31:19
 */
public class RedissonClientTest {
    public static void main(String[] args) {
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("redis://192.168.31.101:7001")
                .addNodeAddress("redis://192.168.31.101:7002")
                .addNodeAddress("redis://192.168.31.101:7003")
                .addNodeAddress("redis://192.168.31.102:7001")
                .addNodeAddress("redis://192.168.31.102:7002")
                .addNodeAddress("redis://192.168.31.102:7003");

        RedissonClient redisson = Redisson.create(config);

        RLock lock = redisson.getLock("anyLock");
        lock.lock();
        lock.unlock();


        RLock lock1 = redisson.getFairLock("lock1");
        RLock lock2 = redisson.getFairLock("lock2");
        RLock lock3 = redisson.getFairLock("lock3");
        RedissonRedLock multiLock = new RedissonRedLock(lock1, lock2, lock3);
        multiLock.lock();
        multiLock.unlock();
    }
}
