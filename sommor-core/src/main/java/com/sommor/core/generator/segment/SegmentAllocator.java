package com.sommor.core.generator.segment;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
public interface SegmentAllocator {

    int length();

    Segment allocate();
}
