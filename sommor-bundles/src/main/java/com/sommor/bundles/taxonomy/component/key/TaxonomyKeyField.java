package com.sommor.bundles.taxonomy.component.key;

import com.sommor.core.model.config.TargetAnnotation;

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
