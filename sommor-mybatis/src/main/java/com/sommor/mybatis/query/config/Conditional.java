package com.sommor.mybatis.query.config;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {
    String operator() default "=";
}
