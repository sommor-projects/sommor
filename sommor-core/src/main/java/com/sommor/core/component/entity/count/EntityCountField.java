package com.sommor.core.component.entity.count;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(EntityCountFieldConfig.class)
public @interface EntityCountField {

    String entityName();

    String entityIdFieldName() default "";
}
