package com.sommor.core.component.currency;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/2
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(CurrencySelectFieldConfig.class)
public @interface CurrencySelectField {
}
