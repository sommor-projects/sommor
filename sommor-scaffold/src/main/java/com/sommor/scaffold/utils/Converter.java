package com.sommor.scaffold.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
public class Converter {

    public static Integer parseInt(Object value) {
        if (null == value) {
            return null;
        }

        if (value instanceof Integer) {
            return (Integer) value;
        }

        String s = value.toString();
        if (StringUtils.isBlank(s)) {
            return null;
        }

        return Integer.parseInt(s);
    }

    public static String convertDateTime(Integer time) {
        if (null == time) {
            return null;
        }

        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault());
        return ftf.format(ldt);
    }
}
