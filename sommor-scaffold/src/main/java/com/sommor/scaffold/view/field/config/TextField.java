package com.sommor.scaffold.view.field.config;

import com.sommor.scaffold.view.TextView;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FieldConfig(TextView.class)
public @interface TextField {

    String title() default "";

    String style() default "text";

    boolean disabled() default false;
}
