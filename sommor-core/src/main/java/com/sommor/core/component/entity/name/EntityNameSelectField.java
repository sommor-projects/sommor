package com.sommor.core.component.entity.name;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetAnnotation(EntityNameSelectConfig.class)
public @interface EntityNameSelectField {

    String title() default "内容主体";

}
