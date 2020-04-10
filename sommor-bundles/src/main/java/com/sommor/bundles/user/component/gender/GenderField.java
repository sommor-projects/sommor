package com.sommor.bundles.user.component.gender;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(GenderFieldConfig.class)
@Documented
public @interface GenderField {

    String title() default  "性别";

}
