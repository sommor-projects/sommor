package com.sommor.scaffold.fields.orderby;

import com.sommor.scaffold.view.TextView;
import com.sommor.scaffold.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FieldConfig(TextView.class)
public @interface OrderByParamField {

    String title() default "排序";

}
