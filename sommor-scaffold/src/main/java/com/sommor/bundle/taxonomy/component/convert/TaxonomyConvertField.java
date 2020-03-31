package com.sommor.bundle.taxonomy.component.convert;

import com.sommor.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/29
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(TaxonomyConvertFieldConfig.class)
@Documented
public @interface TaxonomyConvertField {

    String taxonomyFieldName() default "taxonomy";

}
