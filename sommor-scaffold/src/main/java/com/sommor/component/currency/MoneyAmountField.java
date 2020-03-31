package com.sommor.component.currency;

import com.sommor.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/9
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(MoneyAmountFieldConfig.class)
public @interface MoneyAmountField {

    String title() default "";

    String currencyFieldName() default "currency";
}
