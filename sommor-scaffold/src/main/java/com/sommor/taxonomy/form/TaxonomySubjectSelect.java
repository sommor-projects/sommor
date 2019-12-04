package com.sommor.taxonomy.form;

import com.sommor.view.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldConfig(TaxonomySubjectSelectView.class)
@Documented
public @interface TaxonomySubjectSelect {
}
