package com.sydml.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 注意：json转换为对象时，该对象需要有无参的构造函数（jackson存在该问题，fastjson没有这个问题）
 * <p>
 * Created by Yuming-Liu
 * 日期： 2018-08-07
 * 时间： 23:26
 */
public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private JsonUtil() {
    }

    public static <T> String encodeJson(T object) {
        String json;
        try {
            json = JSON.toJSONStringWithDateFormat(object, DATE_FORMAT, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            LOGGER.error("convert POJO to JSON failure", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    public static <T> T decodeJson(String json, Class<T> type) {
        T pojo;
        try {
            pojo = JSON.parseObject(json, type);
        } catch (Exception e) {
            LOGGER.error("convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }

    public static <T> List<T> decodeArrayJson(String json, Class<T> type) {
        List<T> list;
        try {
            list = JSON.parseArray(json, type);
        } catch (Exception e) {
            LOGGER.error("convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }
        return list;
    }
}
