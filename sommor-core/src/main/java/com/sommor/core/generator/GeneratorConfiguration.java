package com.sommor.core.generator;

import com.google.common.collect.Lists;
import com.sommor.core.generator.segment.random.RandomSegmentAllocator;
import com.sommor.core.generator.segment.sequence.SequenceCacheManager;
import com.sommor.core.generator.segment.sequence.SequenceId;
import com.sommor.core.generator.segment.sequence.SequenceRepository;
import com.sommor.core.generator.segment.sequence.SequenceSegmentAllocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
@Configuration
public class GeneratorConfiguration {

    @Bean
    public SequenceCacheManager sequenceCacheManager(DataSource dataSource) {
        return new SequenceCacheManager(getSequenceRepositories(dataSource, 1));
    }

    private List<SequenceRepository> getSequenceRepositories(DataSource dataSource, int dbSize) {
        List<SequenceRepository> list = new ArrayList<>();
        for (int i=0; i<dbSize; i++) {
            list.add(new SequenceRepository(dataSource, i));
        }

        return list;
    }

}
