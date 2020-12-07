package com.sydml.redis.queue;

import com.sydml.common.utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> extKey = new HashMap<>();
        extKey.put("isReturn", "0");
        extKey.put("returnTime", "3");
        map.put("extKey", extKey);
        System.out.println(JsonUtil.encodeJson(map));
    }
}
