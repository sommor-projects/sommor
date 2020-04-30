package com.sommor.core.model.enricher;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityReferences {

    EntityReference[] value();

}
