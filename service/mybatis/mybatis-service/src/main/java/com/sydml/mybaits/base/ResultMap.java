package com.sydml.mybaits.base;

import com.sydml.common.utils.StringUtil;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 * @author Liuym
 * @date 2019/3/22 0022
 */
public class ResultMap extends HashMap {
    @Override
    public Object put(Object key, Object value) {
        if (key instanceof String) {
            String newKey = StringUtil.underlineToCamel((String) key);
            if (value instanceof Timestamp) {
                value = ((Timestamp) value).toLocalDateTime();
            } else if (value instanceof Date) {
                value = ((Date) value).toLocalDate();
            }
            return super.put(newKey, value);
        } else {
            return super.put(key, value);
        }
    }


    public static void main(String[] args) {
        String wqer_fdas_fads = StringUtil.underlineToCamel("asdf");
        System.out.println(wqer_fdas_fads);
    }
}
