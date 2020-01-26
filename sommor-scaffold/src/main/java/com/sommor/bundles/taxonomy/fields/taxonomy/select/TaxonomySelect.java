package com.sommor.bundles.taxonomy.fields.taxonomy.select;

import com.sommor.scaffold.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@FieldConfig(TaxonomySelectView.class)
@Documented
public @interface TaxonomySelect {

    String title() default "";

    boolean multiple() default false;

    boolean tree() default false;

    boolean rootSelection() default false;

    boolean selfSelection() default false;

    int typeId() default -1;

    String type() default "";

    int parentId() default -1;
}
