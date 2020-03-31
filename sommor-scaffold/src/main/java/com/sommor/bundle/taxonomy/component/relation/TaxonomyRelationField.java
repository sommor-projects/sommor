package com.sommor.bundle.taxonomy.component.relation;

import com.sommor.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(TaxonomyRelationConfig.class)
@Documented
public @interface TaxonomyRelationField {

    String taxonomy() default "";

    String entityName();

    String entityIdFieldName() default "entityId";

    String entityNameFieldName() default "entityName";

    String taxonomyIdFieldName() default "taxonomyId";

}
