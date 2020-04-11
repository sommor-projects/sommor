package com.sommor.bundles.user;

import com.google.common.collect.Lists;
import com.sommor.core.generator.IdGenerator;
import com.sommor.core.generator.segment.random.RandomSegmentAllocator;
import com.sommor.core.generator.segment.sequence.SequenceId;
import com.sommor.core.generator.segment.sequence.SequenceSegmentAllocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/10
 */
@Configuration
public class UserConfiguration {

    @Bean
    public SequenceSegmentAllocator userIdSequenceSegmentAllocator() {
        // |prefix<-1->|dbIndex<-2->|sequence<-21->|random<-1->|
        SequenceId sequenceId = new SequenceId("userId", 21, 0, 10);
        return new SequenceSegmentAllocator(sequenceId);
    }

    @Bean
    public IdGenerator userIdGenerator(SequenceSegmentAllocator userIdSequenceSegmentAllocator) {
        return new IdGenerator(Lists.newArrayList(
                userIdSequenceSegmentAllocator,
                new RandomSegmentAllocator(1)
        ));
    }
}
