package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-07
 * 时间： 22:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void useSessionCallBack() {
        redisTemplate.execute(new SessionCallback() {
            @Nullable
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.opsForHash().putIfAbsent("hash", "test", "value");
                redisOperations.opsForList().rightPushIfPresent("lsit", "java");
                return null;
            }
        });
    }
}
