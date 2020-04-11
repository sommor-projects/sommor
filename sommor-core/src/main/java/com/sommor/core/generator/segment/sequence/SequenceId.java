package com.sommor.core.generator.segment.sequence;

import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
public class SequenceId {

    @Getter
    private String name;

    @Getter
    private long startValue;

    @Getter
    private long stepValue;

    @Getter
    private int length;

    public SequenceId(String name, int length, long startValue, long stepValue) {
        this.name = name;
        this.length = length;
        this.startValue = startValue;
        this.stepValue = stepValue;
    }
}
