package com.sommor.core.component.status;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanguanwei@qq.com
 * @since 2020/5/2
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(StatusFieldConfig.class)
public @interface StatusField {

    String statusFieldName() default "";

}
