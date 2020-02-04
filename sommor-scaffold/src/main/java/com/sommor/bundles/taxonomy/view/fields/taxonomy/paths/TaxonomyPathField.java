package com.sommor.bundles.taxonomy.view.fields.taxonomy.paths;

import com.sommor.core.view.TextView;
import com.sommor.core.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/26
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FieldConfig(TextView.class)
@Documented
public @interface TaxonomyPathField {

    String name();

}
