package com.sommor.bundles.mall.shop;

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
public class ShopConfiguration {

    @Bean
    public SequenceSegmentAllocator shopIdSequenceSegmentAllocator() {
        // |prefix<-1->|dbIndex<-2->|sequence<-16->|random<-1->|
        SequenceId sequenceId = new SequenceId("shopId", 16, 0, 10);
        return new SequenceSegmentAllocator(sequenceId);
    }

    @Bean
    public IdGenerator shopIdGenerator(SequenceSegmentAllocator shopIdSequenceSegmentAllocator) {
        return new IdGenerator(Lists.newArrayList(
                shopIdSequenceSegmentAllocator,
                new RandomSegmentAllocator(1)
        ));
    }
}
