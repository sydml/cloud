package com.sydml.common.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 自己实现的ThreadLocal
 * <p>
 * Created by Yuming-Liu
 * 日期： 2018-08-13
 * 时间： 22:42
 */
public class MyThreadLocal<T> {

    private Map<Thread, T> container = Collections.synchronizedMap(new HashMap<Thread, T>());

    public void set(T value) {
        container.put(Thread.currentThread(), value);
    }

    public T get() {
        Thread thread = Thread.currentThread();
        T value = container.get(thread);
        if (value == null && !container.containsKey(thread)) {
            value = initialVlaue();
            container.put(thread, value);
        }
        return value;
    }

    public void remove() {
        container.remove(Thread.currentThread());
    }

    private T initialVlaue() {
        return null;
    }
}
