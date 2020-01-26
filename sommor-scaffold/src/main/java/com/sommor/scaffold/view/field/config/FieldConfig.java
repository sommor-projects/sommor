package com.sommor.scaffold.view.field.config;

import com.sommor.scaffold.view.FieldView;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldConfig {

    Class<? extends FieldView> value() default FieldView.class;

    boolean processType() default false;

}
