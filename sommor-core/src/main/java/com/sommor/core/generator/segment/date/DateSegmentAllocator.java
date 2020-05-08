package com.sommor.core.generator.segment.date;

import com.sommor.core.generator.segment.Segment;
import com.sommor.core.generator.segment.SegmentAllocator;
import com.sommor.core.utils.Converter;
import com.sommor.core.utils.DateTimeUtil;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/8
 */
public class DateSegmentAllocator implements SegmentAllocator {

    private static final int LENGTH = 20;

    @Override
    public int length() {
        return LENGTH;
    }

    @Override
    public Segment allocate() {
        return new Segment(Converter.parseLong(DateTimeUtil.format("yyMMdd")));
    }
}
