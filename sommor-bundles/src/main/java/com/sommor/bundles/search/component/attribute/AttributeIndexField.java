package com.sommor.bundles.search.component.attribute;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(AttributeIndexFieldConfig.class)
@Documented
public @interface AttributeIndexField {

    String entityNameFieldName() default "entityName";

}
