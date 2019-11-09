package com.sommor.extensibility.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@Import(SommorPluginConfigurationSelector.class)
@Configuration
@ComponentScan(basePackages = {
    "com.sommor.extensibility.spring"
})
public class SommorConfiguration {

}
