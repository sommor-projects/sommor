package com.sommor.core;

import com.sommor.extensibility.SommorExtensibilityConfiguration;
import com.sommor.mybatis.SommorMybatisConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

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
