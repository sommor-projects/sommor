package com.sommor.core.utils;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/8
 */
public class BitUtil {

    public static int calcBitLength(long value) {
        int length = 0;
        while (value > 0) {
            value = value >>> 1;
            length++;
        }
        return length;
    }
}
