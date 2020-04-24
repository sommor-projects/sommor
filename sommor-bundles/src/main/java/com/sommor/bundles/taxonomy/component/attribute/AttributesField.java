package com.sommor.bundles.taxonomy.component.attribute;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(AttributesFieldConfig.class)
@Documented
public @interface AttributesField {

    String attributesFieldName() default "attributes";
}
