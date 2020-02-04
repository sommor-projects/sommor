package com.sommor.core.view.field.config;

import com.sommor.core.view.FormFieldView;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldConfig {

    Class<? extends FormFieldView> value() default FormFieldView.class;

    boolean processType() default false;

}
