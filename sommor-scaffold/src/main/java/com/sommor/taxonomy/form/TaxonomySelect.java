package com.sommor.taxonomy.form;

import com.sommor.view.config.FieldConfig;

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

    boolean multiple() default false;

    boolean tree() default false;

    boolean rootSelection() default false;

    int typeId() default -1;

    String type() default "";

    int parentId() default -1;
}
