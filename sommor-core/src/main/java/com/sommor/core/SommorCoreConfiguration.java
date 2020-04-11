package com.sommor.core;

import com.google.common.collect.Lists;
import com.sommor.core.generator.segment.sequence.SequenceCacheManager;
import com.sommor.core.generator.segment.sequence.SequenceRepository;
import com.sommor.extensibility.SommorExtensibilityConfiguration;
import com.sommor.mybatis.SommorMybatisConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/10
 */
@SpringBootApplication
@Import({
        SommorMybatisConfiguration.class,
        SommorExtensibilityConfiguration.class
})
public class SommorCoreConfiguration {

}
