package com.sommor.taxonomy.form;

import com.sommor.view.config.FieldConfig;
import com.sommor.view.config.Fieldset;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/3
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Fieldset
@Documented
public @interface SubjectTaxonomySelection {
}
