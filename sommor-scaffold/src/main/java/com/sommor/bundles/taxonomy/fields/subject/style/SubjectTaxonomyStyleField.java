package com.sommor.bundles.taxonomy.fields.subject.style;

import com.sommor.scaffold.view.SelectView;
import com.sommor.scaffold.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FieldConfig(SelectView.class)
@Documented
public @interface SubjectTaxonomyStyleField {

    String title() default "";

}
