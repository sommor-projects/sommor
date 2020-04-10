package com.sommor.extensibility;

import com.sommor.extensibility.spring.SommorPluginConfigurationSelector;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/10
 */
@Configuration
@Import({
        SommorPluginConfigurationSelector.class
})
@ComponentScan("com.sommor.extensibility")
public class SommorExtensibilityConfiguration {
}
