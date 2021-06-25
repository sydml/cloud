package com.sydml.authorization.controller;

import com.sydml.common.utils.StreamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liuym
 * @date 2019/3/28 0028
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("test")
    public String testLua() {
//        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("lua/string.lua");
//        String lua = StreamUtil.getString(inputStream);
//        RedisScript redisScript = RedisScript.of(lua, Long.class);
//        List<String> list = new ArrayList<>();
//        list.add("test");
//        redisTemplate.execute((session)->{})
//        Object execute = redisTemplate.execute(redisScript, list, "60");
//        Object execute = redisTemplate
        return "String test";
    }


}
