package com.sommor.core.generator.segment.sequence;

import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/10
 */
public class SequenceResult {

    @Getter
    private long prefix;

    @Getter
    private long value;

    public SequenceResult(long prefix, long value) {
        this.prefix = prefix;
        this.value = value;
    }
}
