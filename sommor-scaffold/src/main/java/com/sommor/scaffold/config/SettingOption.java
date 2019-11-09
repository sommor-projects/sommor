package com.sommor.scaffold.config;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/27
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SettingOption {
    String code() default "";

    String desc();
}
