package com.sommor.media.form;

import com.sommor.view.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/4
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@FieldConfig(value = PicturesFieldView.class, processType = true)
@Documented
public @interface PicturesFieldConfig {

    int maxCount() default 1;

}
