package com.sydml.common.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 均按照系统时区处理
 * 若需处理时区使用DateTimeFormatter.ofPattern(pattern).withZone()
 *
 * @author Liuym
 * @date 2019/3/23 0023
 */
public class TimeUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static String instantToString(Instant instant, String pattern) {
        pattern = StringUtil.isEmpty(pattern) ? YYYY_MM_DD_HH_MM_SS : pattern;
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
        return df.format(instant);
    }

    public static Instant systemCurrentMillsToInstant(long systemCurrentMills) {
        return Instant.ofEpochMilli(systemCurrentMills);
    }

    public static LocalDateTime stringToLocalDateTime(String localDateTimeString, String pattern) {
        pattern = StringUtil.isEmpty(pattern) ? YYYY_MM_DD_HH_MM_SS : pattern;
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(localDateTimeString, df);
    }

    public static String localDateTimeToString(LocalDateTime localDateTime, String pattern) {
        pattern = StringUtil.isEmpty(pattern) ? YYYY_MM_DD_HH_MM_SS : pattern;
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(localDateTime);
    }

    public static String LocalDateToString(LocalDate localDate, String pattern) {
        pattern = StringUtil.isEmpty(pattern) ? YYYY_MM_DD : pattern;
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(localDate);
    }

    public static LocalDate stringToLocalDate(String localDateString) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(YYYY_MM_DD);
        return LocalDate.parse(localDateString, df);
    }

}
