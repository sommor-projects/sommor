package com.sommor.bundle.taxonomy.component.select;

import com.sommor.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(TaxonomySelectFieldConfig.class)
@Documented
public @interface TaxonomySelectField {

    String title() default "";

    boolean multiple() default false;

    boolean tree() default false;

    boolean includeRoot() default false;

    boolean includeSelf() default false;

    int typeId() default -1;

    String type() default "";

    String group() default "";

    int parentId() default -1;
}
