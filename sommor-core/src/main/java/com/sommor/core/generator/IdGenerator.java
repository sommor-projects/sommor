package com.sommor.core.generator;

import com.sommor.core.generator.segment.Segment;
import com.sommor.core.generator.segment.SegmentAllocator;
import lombok.Getter;

import java.util.List;
/**
 * @author yanguanwei@qq.com
 * @since 2020/4/10
 */
public class IdGenerator {

    @Getter
    private int length;

    private List<SegmentAllocator> segmentAllocators;

    public IdGenerator(List<SegmentAllocator> segmentAllocators) {
        this.segmentAllocators = segmentAllocators;
        this.length = segmentAllocators.stream().mapToInt(SegmentAllocator::length).sum();
    }

    private int calculateDecimalLength(int length) {
        return String.valueOf((1 << length) - 1).length();
    }

    public long generateId() {
        int length = 0;
        long id = 0;

        for (SegmentAllocator segmentAllocator : segmentAllocators) {
            Segment segment = segmentAllocator.allocate();
            long value = segment.getValue();
            int valueLength = segment.getLength();

            if (length > 0) {
                id = (id << valueLength) | value;
            } else {
                id = value;
            }

            length += valueLength;
        }

        return id;
    }
}
