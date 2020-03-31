package com.sommor.bundle.taxonomy.component.path;

import com.sommor.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/26
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(TaxonomyPathFieldConfig.class)
@Documented
public @interface TaxonomyPathField {

    int taxonomyId() default 0;

    String taxonomyIdFieldName() default "taxonomyId";

}
