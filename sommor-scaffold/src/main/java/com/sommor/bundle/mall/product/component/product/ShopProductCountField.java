package com.sommor.bundle.mall.product.component.product;

import com.sommor.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/7
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(ShopProductCountFieldConfig.class)
@Documented
public @interface ShopProductCountField {

     String title() default "";

     String shopIdFieldName() default "shopId";

}
