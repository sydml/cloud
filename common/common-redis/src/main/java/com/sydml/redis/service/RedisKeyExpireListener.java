package com.sydml.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuyuming
 * @date 2021-06-30 16:16:04
 * redis监听key过期事件
 */
@Component
@Slf4j
public class RedisKeyExpireListener extends JedisPubSub{
    @Resource
    private Jedis jedis;

    /**
     * 会导致请求夯住，遗留问题
     * @param pattern
     * @param subscribedChannels
     */
    /*@PostConstruct
    public void init(){
        String parameter = "notify-keyspace-events";
        List<String> notify = jedis.configGet(parameter);
        if (notify.get(1).equals("")) {
            //过期事件
            jedis.configSet(parameter, "Ex");
        }
        jedis.psubscribe(new RedisKeyExpireListener(), "__keyevent@0__:expired");
        log.info("开始执redis key 过期行监听配置");
    }*/

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        log.info("[Redis过期线程]启动成功!");
        log.info("启动Redis过期事件通知： " + pattern + "" + subscribedChannels);
    }
    @Override
    public void onPMessage(String pattern, String channel, String message) {
        if ("testExpireKey".equals(message)) {
            log.info("testExpireKey expire key is {}", message);
        }
    }
}
