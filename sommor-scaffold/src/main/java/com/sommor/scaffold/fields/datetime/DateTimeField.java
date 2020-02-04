package com.sommor.scaffold.fields.datetime;

import com.sommor.core.view.TextView;
import com.sommor.core.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FieldConfig(TextView.class)
public @interface DateTimeField {
}
