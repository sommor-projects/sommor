package com.sommor.core.model.enricher;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/8
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(EntityReferenceConfig.class)
@Repeatable(EntityReferences.class)
public @interface EntityReference {

    String entity();

    String byField();

    String refField() default "id";

    Class target() default Void.class;
}
