package com.sommor.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
public class Converter {

    public static String toString(Object value) {
        if (null == value) {
            return null;
        }

        return value.toString();
    }

    public static Long parseLong(Object value) {
        if (null == value) {
            return null;
        }

        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }

        if (value instanceof Long) {
            return (Long) value;
        }

        String s = value.toString();
        if (StringUtils.isBlank(s)) {
            return null;
        }

        return Long.parseLong(s);
    }

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

    public static byte[] convertBytes(InputStream is) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedInputStream in = null;
        try {
            try {
                in = new BufferedInputStream(is);
                int bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len = 0;
                while (-1 != (len = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len);
                }
                return bos.toByteArray();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bos.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
