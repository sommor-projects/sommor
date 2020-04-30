package com.sommor.bundles.taxonomy.component.attribute;

import com.sommor.core.model.config.TargetAnnotation;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/17
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@TargetAnnotation(AttributeSelectionConfig.class)
@Documented
public @interface AttributeSelectionField {

    String taxonomy() default "";

    String entityName();

    String entityIdFieldName() default "entityId";

    String taxonomyFieldName() default "taxonomy";

    String attributesFieldName() default "attributes";

}
