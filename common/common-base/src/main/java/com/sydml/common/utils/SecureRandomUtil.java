package com.sydml.common.utils;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author liuyuming
 * @date 2021-06-08 10:41:26
 * SecureRandom 同步，性能较差，线程安全，可以指定算法，比Random性能还差
 * Random 线程安全，同步，使用系统时间纳秒作为种子
 * ThreadLocalRandom 线程安全，并发，使用系统时间纳秒作为种子
 */
public class SecureRandomUtil {
    public static SecureRandom random = new SecureRandom();
    public static ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    public static String getRandom(int length) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < length; i++) {
            boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
            if (isChar) { // 字符串
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                ret.append((char) (choice + random.nextInt(26)));
            } else { // 数字
                ret.append(random.nextInt(10));
            }
        }
        return ret.toString();
    }

    /**
     * 生成指定长度的随机数
     * @param length
     * @return
     */
    public static int getSpecifiedLength(int length) {
        return threadLocalRandom.nextInt((int)Math.pow(10, length - 1), (int)Math.pow(10, length));
    }
}
