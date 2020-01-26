package com.sommor.mybatis.query.config;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryConfig {

    boolean enablePageable() default false;

    boolean enableOrderly() default false;

    String[] searchFieldsOfKeywords() default {};

}
