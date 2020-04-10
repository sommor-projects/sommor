package com.sommor.core.utils;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/15
 */
public class DateTimeUtil {

    public static int now() {
        return (int) (System.currentTimeMillis() / 1000);
    }
}
