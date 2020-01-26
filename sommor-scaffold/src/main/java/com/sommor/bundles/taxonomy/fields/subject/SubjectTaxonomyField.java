package com.sommor.bundles.taxonomy.fields.subject;

import com.sommor.scaffold.view.FieldsetView;
import com.sommor.scaffold.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FieldConfig(FieldsetView.class)
@Documented
public @interface SubjectTaxonomyField {

    String type() default "";

}
