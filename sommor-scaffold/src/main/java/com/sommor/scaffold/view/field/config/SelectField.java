package com.sommor.scaffold.view.field.config;

import com.sommor.scaffold.view.SelectView;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FieldConfig(SelectView.class)
public @interface SelectField {

    String title() default "";


}