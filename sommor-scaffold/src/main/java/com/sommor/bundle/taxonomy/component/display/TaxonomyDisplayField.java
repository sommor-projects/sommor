package com.sommor.bundle.taxonomy.component.display;

import com.sommor.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation
@Documented
public @interface TaxonomyDisplayField {

    String entityName();

    String entityIdFieldName() default "id";

    String type();

    boolean displayPaths() default false;
}
