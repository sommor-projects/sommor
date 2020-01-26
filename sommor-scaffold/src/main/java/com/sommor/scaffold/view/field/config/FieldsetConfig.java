package com.sommor.scaffold.view.field.config;

import com.sommor.scaffold.view.FieldsetView;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/26
 */
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FieldConfig(FieldsetView.class)
public @interface FieldsetConfig {

    Class value() default Void.class;

    boolean processFieldType() default false;
}
