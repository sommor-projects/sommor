package com.sommor.bundles.taxonomy.view.fields.subject.style;

import com.sommor.core.view.SelectView;
import com.sommor.core.view.field.config.FieldConfig;

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
