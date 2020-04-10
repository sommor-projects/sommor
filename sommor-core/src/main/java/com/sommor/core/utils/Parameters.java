package com.sommor.core.utils;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class Parameters {

    public static <T> T get(T value, T defaultValue) {
        return null == value ? defaultValue : value;
    }
}
