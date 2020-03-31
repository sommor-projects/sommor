package com.sommor.component.entity.select;

import com.sommor.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(EntitySelectFieldConfig.class)
public @interface EntitySelectField {

    String title();

    String entityName();

    String[] entityConditions() default {};
}
