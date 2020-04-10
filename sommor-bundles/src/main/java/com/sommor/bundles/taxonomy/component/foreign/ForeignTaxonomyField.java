package com.sommor.bundles.taxonomy.component.foreign;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * Taxonomy分类外键字段
 *
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(TaxonomyForeignFieldConfig.class)
@Documented
public @interface ForeignTaxonomyField {

    /**
     * 外键字段名，默认为模型中声明的字段
     */
    String taxonomyFieldName() default "";

    String typeFieldName() default "";
}
