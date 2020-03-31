package com.sommor.view.field.text;

import com.sommor.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(TextFieldConfig.class)
public @interface TextField {
    String title() default "";
}
