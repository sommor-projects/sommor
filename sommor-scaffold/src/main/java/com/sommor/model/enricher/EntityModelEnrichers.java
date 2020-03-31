package com.sommor.model.enricher;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityModelEnrichers {

    EntityModelEnricher[] value();

}
