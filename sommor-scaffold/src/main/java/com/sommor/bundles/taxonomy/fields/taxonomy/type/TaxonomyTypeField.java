package com.sommor.bundles.taxonomy.fields.taxonomy.type;

import com.sommor.scaffold.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldConfig
@Documented
public @interface TaxonomyTypeField {

    String name() default "";

}
