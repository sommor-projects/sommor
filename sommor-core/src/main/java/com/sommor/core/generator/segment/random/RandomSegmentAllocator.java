package com.sommor.core.generator.segment.random;

import com.sommor.core.generator.segment.Segment;
import com.sommor.core.generator.segment.SegmentAllocator;

import java.util.Random;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
public class RandomSegmentAllocator implements SegmentAllocator {

    private int length;

    private int maxValue;

    private Random random;

    public RandomSegmentAllocator(int length) {
        this.length = length;
        this.maxValue = 1 << length;
        this.random = new Random();
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public Segment allocate() {
        long value = this.random.nextInt(this.maxValue);
        return new Segment(this.length, value);
    }
}
