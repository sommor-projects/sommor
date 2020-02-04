package com.sommor.scaffold.fields.currency;

import com.sommor.core.view.SelectView;
import com.sommor.core.view.TextView;
import com.sommor.core.view.field.config.FieldConfig;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FieldConfig(SelectView.class)
public @interface CurrencySelectField {
}
