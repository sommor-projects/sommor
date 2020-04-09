package com.sommor.bundle.taxonomy.component.key;

import com.sommor.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/4
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(TaxonomyKeyFieldConfig.class)
@Documented
public @interface TaxonomyKeyField {
}
