package com.sommor.bundles.taxonomy.component.style;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(TaxonomyStyleFieldConfig.class)
@Documented
public @interface TaxonomyStyleField {

    String title() default "";

}
