package com.sommor.core.component.entity.select;

import com.sommor.core.model.config.TargetAnnotation;

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
