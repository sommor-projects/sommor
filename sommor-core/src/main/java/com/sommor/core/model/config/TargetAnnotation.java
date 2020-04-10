package com.sommor.core.model.config;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetAnnotation {

    Class<? extends TargetConfig> value() default TargetConfig.class;

}
