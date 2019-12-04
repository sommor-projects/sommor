package com.sommor.view.config;

import com.sommor.view.FieldView;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldConfig {

    Class<? extends FieldView> value();

    boolean processType() default false;

}
