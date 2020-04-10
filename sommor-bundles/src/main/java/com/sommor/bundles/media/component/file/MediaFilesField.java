package com.sommor.bundles.media.component.file;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/4
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(MediaFilesFieldConfig.class)
@Documented
public @interface MediaFilesField {

    int maxCount() default 1;

    String title() default "";

    String entity();

    String entityIdFieldName() default "id";

    String coverFieldName() default "";

}
