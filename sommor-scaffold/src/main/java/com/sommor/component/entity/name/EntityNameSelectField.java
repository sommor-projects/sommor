package com.sommor.component.entity.name;

import com.sommor.model.config.TargetAnnotation;

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
