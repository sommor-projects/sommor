package com.sommor.mybatis.entity.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Table {

    /**
     * 表名
     */
    String value() default "";

    String subject() default "";

    String primaryKey() default "id";
}
