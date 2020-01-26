package com.sommor.bundles.media.fields.file;

import com.sommor.scaffold.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/4
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FieldConfig(value = MediaFilesView.class, processType = true)
@Documented
public @interface MediaFilesField {

    int maxCount() default 1;

    String title() default "";

    String entityField() default "";

}
