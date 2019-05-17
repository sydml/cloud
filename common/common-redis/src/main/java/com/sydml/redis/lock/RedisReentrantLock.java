package com.sydml.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-09
 * 时间： 14:56
 */
@Component
public class RedisReentrantLock {

    private ThreadLocal<Map<String, Integer>> lockers = new ThreadLocal<>();

    @Autowired
    private Jedis jedis;

    public boolean lock(String key) {
        Map<String, Integer> refs = currentLocks();
        Integer refCnt = refs.get(key);
        if (refCnt != null) {
            refs.put(key, refCnt + 1);
            return true;
        }

        boolean locked = tryLock(key);
        if (!locked) {
            return false;
        }
        refs.put(key, 1);
        return true;

    }

    public boolean unlock(String key) {
        Map<String, Integer> refs = currentLocks();
        Integer refCnt = refs.get(key);
        if (refCnt == null) {
            return false;
        }
        refCnt--;
        if (refCnt > 0) {
            refs.put(key, refCnt);
            return true;

        } else {
            refs.remove(key);
            return tryUnlock(key);
        }
    }

    private boolean tryLock(String key) {
        return jedis.set(key, "", "nx", "ex", 5L) != null;
    }

    private boolean tryUnlock(String key) {
        return jedis.del(key) > 0;
    }

    private Map<String, Integer> currentLocks() {
        Map<String, Integer> refs = lockers.get();
        if (refs != null) {
            return refs;
        }

        lockers.set(new HashMap<>());
        return lockers.get();
    }

}
