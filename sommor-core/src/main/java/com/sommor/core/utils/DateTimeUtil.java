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

    public static int now() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static String formatDateTime(Integer time) {
        return format(time, DATE_TIME_FORMAT);
    }

    public static String formatDate(Integer time) {
        return format(time, DATE_FORMAT);
    }

    public static String format(Integer time, String format) {
        if (null == time) {
            return null;
        }

        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(format);
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());
        return ftf.format(ldt);
    }

    public static Integer parseDate(String formattedDate) {
        LocalDate parse = LocalDate.parse(formattedDate);
        return (int) (parse.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000);
    }

    public static void main(String[] args) {
        System.out.println(formatDate(1587657600));
    }
}
