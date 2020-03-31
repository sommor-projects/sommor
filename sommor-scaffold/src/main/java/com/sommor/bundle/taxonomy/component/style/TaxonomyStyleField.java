package com.sommor.bundle.taxonomy.component.style;

import com.sommor.model.config.TargetAnnotation;

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
