package com.sommor.core.generator.segment.sequence;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/10
 */
public class SequenceCache {

    @Getter
    private String name;

    @Getter
    private int dbIndex;

    @Getter
    private long prefix;

    /**
     * value excluded
     */
    @Getter
    private long minValue;

    /**
     * value included
     */
    @Getter
    private long maxValue;

    private AtomicLong currentValue;

    public SequenceCache(String name, int dbIndex, long prefix, long minValue, long maxValue) {
        this.name = name;
        this.dbIndex = dbIndex;
        this.prefix = prefix;
        this.minValue = minValue;
        this.maxValue = maxValue;

        currentValue = new AtomicLong(minValue);
    }

    public long next() {
        long current = currentValue.addAndGet(1);
        if (current > maxValue) {
            return -1;
        }
        return current;
    }

    public long current() {
        return currentValue.get();
    }

    public boolean hasNext() {
        return current() < maxValue;
    }
}
