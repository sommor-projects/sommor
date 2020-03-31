package com.sommor.bundle.mall.product.component.shop;

import com.sommor.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(ShopDisplayFieldConfig.class)
@Documented
public @interface ShopDisplayField {

    String shopIdFieldName() default "shopId";

}
