package com.sommor.model.define;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/5
 */
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModelAware {

    /**
     * Model Class
     */
    Class value() default Void.class;
}
