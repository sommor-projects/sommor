package com.sommor.core.generator.segment;

import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
public class Segment {

    @Getter
    private int length;

    @Getter
    private long value;

    public Segment(int length, long value) {
        this.length = length;
        this.value = value;
    }
}
