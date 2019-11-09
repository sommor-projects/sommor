package com.sommor.extensibility.config;

import com.sommor.extensibility.Priorities;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * 插件配置
 *
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan
public @interface PluginConfig {

    String code() default "";

    String name();

    int priority() default Priorities.DEFAULT;
}
