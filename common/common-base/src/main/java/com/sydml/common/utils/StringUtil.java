package com.sydml.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Yuming-Liu
 * 日期： 2018-07-31
 * 时间： 00:02
 */
public final class StringUtil {

    public static final String SEPARATOR = String.valueOf((char) 29);

    private static final String UNDERLINE = "_";

    private static final String SET = "set";

    private static final String GET = "get";

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String string) {
        if (string != null) {
            string = string.trim();
        }
        return StringUtils.isEmpty(string);
    }

    /**
     * 判断是否为非空
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }


    public static String[] splitString(String body, String regex) {
        return body.split(regex);
    }

    public static String underlineToCamel(String key) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();

        if (key.contains(UNDERLINE)) {
            String[] split = key.split(UNDERLINE);
            for (String s : split) {
                stringBuilder.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
            }
        } else {
            stringBuilder.append(key);
        }
        String result = stringBuilder.replace(0, 1, stringBuilder.substring(0, 1).toLowerCase()).toString();
        return result;
    }

    public static String getSetMethodName(String fieldName) {
        String fieldFirst = fieldName.substring(0, 1).toUpperCase();
        return SET + fieldFirst + fieldName.substring(1, fieldName.length());
    }

    public static String getGetMethodName(String fieldName) {
        String fieldFirst = fieldName.substring(0, 1).toUpperCase();
        return GET + fieldFirst + fieldName.substring(1, fieldName.length());
    }
}
