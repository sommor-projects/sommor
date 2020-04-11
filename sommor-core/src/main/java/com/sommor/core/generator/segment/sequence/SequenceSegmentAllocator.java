package com.sommor.core.generator.segment.sequence;

import com.sommor.core.generator.segment.Segment;
import com.sommor.core.generator.segment.SegmentAllocator;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
public class SequenceSegmentAllocator implements SegmentAllocator {

    private static final int DB_INDEX_LENGTH = 2;

    private static final int PREFIX_LENGTH = 1;

    private SequenceId sequenceId;

    @Resource
    private SequenceCacheManager sequenceCacheManager;

    private long maxSequence;

    public SequenceSegmentAllocator(SequenceId sequenceId) {
        this.sequenceId = sequenceId;
        this.maxSequence = calculateMaxSequence(sequenceId.getLength());
    }

    @Override
    public int length() {
        return this.sequenceId.getLength() + DB_INDEX_LENGTH + PREFIX_LENGTH;
    }

    @Override
    public Segment allocate() {
        SequenceCache sequenceCache;

        long sequence;
        SequenceId sequenceId = this.sequenceId;
        long maxSequence = this.maxSequence;
        SequenceCacheManager sequenceCacheManager = this.sequenceCacheManager;

        do {
            sequenceCache = sequenceCacheManager.getSequenceCache(sequenceId);
            sequence = sequenceCache.next();
        } while (needRefresh(sequence));

        long value = sequence & maxSequence;
        long prefixValue = sequence / maxSequence + 1;

        int bitLength = this.sequenceId.getLength();

        value = (value << DB_INDEX_LENGTH) | sequenceCache.getDbIndex();
        bitLength += DB_INDEX_LENGTH;

        value = (prefixValue << bitLength) | value;
        bitLength += calculateBitLength(prefixValue);

        return new Segment(bitLength, value);
    }

    private int calculateBitLength(long value) {
        int length = 0;
        while (value > 0) {
            value = value >>> 1;
            length++;
        }
        return length;
    }

    private long calculateMaxSequence(int length) {
        return (1 << length) - 1;
    }

    private boolean needRefresh(long sequence) {
        if (sequence < 0) {
            return true;
        }

        return false;
    }

}
