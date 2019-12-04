package com.sommor.extensibility.config;

import java.lang.annotation.*;

/**
 *
 * 扩展定义
 *
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Extension {

    String name();

    boolean annotated() default true;

}
