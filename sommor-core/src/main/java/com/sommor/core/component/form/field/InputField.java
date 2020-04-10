package com.sommor.core.component.form.field;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(InputViewConfig.class)
public @interface InputField {

    String title() default "";

    String style() default "text";

    boolean disabled() default false;
}
