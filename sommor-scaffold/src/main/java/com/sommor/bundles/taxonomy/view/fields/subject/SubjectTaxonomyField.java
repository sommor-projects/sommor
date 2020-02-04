package com.sommor.bundles.taxonomy.view.fields.subject;

import com.sommor.core.view.FieldsetView;
import com.sommor.core.view.field.config.FieldConfig;

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

    String taxonomy() default "";

}
