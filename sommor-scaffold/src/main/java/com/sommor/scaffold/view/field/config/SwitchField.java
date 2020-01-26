package com.sommor.scaffold.view.field.config;

import com.sommor.scaffold.view.SwitchView;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FieldConfig(SwitchView.class)
public @interface SwitchField {
    String title() default "";
}
