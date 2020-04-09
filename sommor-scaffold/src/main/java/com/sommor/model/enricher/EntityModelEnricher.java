package com.sommor.model.enricher;

import com.sommor.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(EntityModelEnrichConfig.class)
@Repeatable(EntityModelEnrichers.class)
public @interface EntityModelEnricher {

    String entityName();

    /**
     * entityId的字段名
     */
    String entityIdFieldName() default "entityId";

    String[] entityFieldNames() default {};
}
