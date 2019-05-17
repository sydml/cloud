package com.sydml.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by Yuming-Liu
 * 日期： 2018-08-06
 * 时间： 22:57
 */
public final class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        method.setAccessible(true);
        try {
            result = method.invoke(obj, args);
        } catch (Exception e) {
            LOGGER.error("invoke method failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     */
    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            LOGGER.error("set field failure", e);
            throw new RuntimeException(e);
        }
    }

    public static Object getProperty(Object obj, String name) throws NoSuchFieldException {
        Object value = null;
        Field field = getField(obj.getClass(), name);
        if (field == null) {
            throw new NoSuchFieldException("no such field [" + name + "]");
        }
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            value = field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        field.setAccessible(accessible);
        return value;
    }

    public static List<Field> getFields(Class clazz) {
        List<Field> result = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        Optional.ofNullable(clazz.getSuperclass()).ifPresent(addSuperclassFields(result));
        return result;
    }

    private static Consumer<Class> addSuperclassFields(List<Field> result) {
        return superClass -> {
            List<Field> superclassFields = getFields(superClass);
            List<String> resultNames = result.stream().map(Field::getName).collect(Collectors.toList());
            List<Field> validSuperclassFields =
                    superclassFields.stream().filter(superclassField -> !resultNames.contains(superclassField.getName())).collect(Collectors.toList());
            result.addAll(validSuperclassFields);
        };
    }

    public static Field getField(Class<?> clazz, String name) {
        try {
            return clazz.getField(name);
        } catch (NoSuchFieldException ex) {
            return getDeclaredField(clazz, name);
        }
    }

    public static Field getDeclaredField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null) {
                return getDeclaredField(clazz.getSuperclass(), name);
            }
            return null;
        }
    }
}
