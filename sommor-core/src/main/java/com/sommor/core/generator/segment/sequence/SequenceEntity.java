package com.sommor.core.generator.segment.sequence;

import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/10
 */
public class SequenceEntity {

    @Getter
    private String name;

    @Getter
    private long prefix;

    @Getter
    private long value;

    public SequenceEntity(String name, long prefix, long value) {
        this.name = name;
        this.prefix = prefix;
        this.value = value;
    }
}
