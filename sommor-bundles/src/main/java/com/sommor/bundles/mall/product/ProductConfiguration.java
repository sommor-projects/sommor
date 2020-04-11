package com.sommor.bundles.mall.product;

import com.google.common.collect.Lists;
import com.sommor.core.generator.IdGenerator;
import com.sommor.core.generator.segment.random.RandomSegmentAllocator;
import com.sommor.core.generator.segment.sequence.SequenceId;
import com.sommor.core.generator.segment.sequence.SequenceSegmentAllocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
@Configuration
public class ProductConfiguration {

    @Bean
    public SequenceSegmentAllocator productIdSequenceSegmentAllocator() {
        // |prefix<-1->|dbIndex<-2->|sequence<-22->|random<-1->|
        SequenceId sequenceId = new SequenceId("productId", 24, 0, 10);
        return new SequenceSegmentAllocator(sequenceId);
    }

    @Bean
    public IdGenerator productIdGenerator(SequenceSegmentAllocator productIdSequenceSegmentAllocator) {
        return new IdGenerator(Lists.newArrayList(
                productIdSequenceSegmentAllocator,
                new RandomSegmentAllocator(1)
        ));
    }

    @Bean
    public SequenceSegmentAllocator skuIdSequenceSegmentAllocator() {
        // |prefix<-1->|dbIndex<-2->|sequence<-22->|random<-1->|
        SequenceId sequenceId = new SequenceId("skuId", 27, 0, 10);
        return new SequenceSegmentAllocator(sequenceId);
    }

    @Bean
    public IdGenerator skuIdGenerator(SequenceSegmentAllocator skuIdSequenceSegmentAllocator) {
        return new IdGenerator(Lists.newArrayList(
                skuIdSequenceSegmentAllocator,
                new RandomSegmentAllocator(1)
        ));
    }
}
