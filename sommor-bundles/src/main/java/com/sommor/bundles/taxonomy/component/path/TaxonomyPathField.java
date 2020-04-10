package com.sommor.bundles.taxonomy.component.path;

import com.sommor.core.model.config.TargetAnnotation;

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

    String taxonomyFieldName() default "taxonomy";

    String typeFieldName() default "";
}
