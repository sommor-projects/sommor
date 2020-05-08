package com.sommor.core.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/15
 */
public class DateTimeUtil {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static long now() {
        return System.currentTimeMillis();
    }

    public static String formatDateTime(Long time) {
        return format(time, DATE_TIME_FORMAT);
    }

    public static String formatDate(Long time) {
        return format(time, DATE_FORMAT);
    }

    public static String format(String format) {
        long now = now();
        return format(now, format);
    }

    public static String format(Long time, String format) {
        if (null == time) {
            return null;
        }

        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(format);
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        return ftf.format(ldt);
    }

    public static Long parseDate(String formattedDate) {
        LocalDate parse = LocalDate.parse(formattedDate);
        return parse.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
