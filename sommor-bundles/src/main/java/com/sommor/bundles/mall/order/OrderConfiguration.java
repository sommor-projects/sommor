package com.sommor.bundles.mall.order;

import com.google.common.collect.Lists;
import com.sommor.core.generator.IdGenerator;
import com.sommor.core.generator.segment.random.RandomSegmentAllocator;
import com.sommor.core.generator.segment.sequence.SequenceId;
import com.sommor.core.generator.segment.sequence.SequenceSegmentAllocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@Configuration
public class OrderConfiguration {
    @Bean
    public SequenceSegmentAllocator orderIdSequenceSegmentAllocator() {
        // |prefix<-1->|sequence<-22->|random<-1->|
        SequenceId sequenceId = new SequenceId("orderId", 26, 0, 10);
        return new SequenceSegmentAllocator(sequenceId);
    }

    @Bean
    public IdGenerator orderIdGenerator(SequenceSegmentAllocator orderIdSequenceSegmentAllocator) {
        return new IdGenerator(Lists.newArrayList(
                orderIdSequenceSegmentAllocator,
                new RandomSegmentAllocator(1)
        ));
    }

}
