package com.sommor.bundles.taxonomy.component.attribute;

import com.sommor.core.view.field.FieldsetConfig;
import com.sommor.core.view.field.FieldsetView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/14
 */
public class AttributeSelectionConfig extends FieldsetConfig<FieldsetView> {

    @Getter
    @Setter
    private String entityName;

    @Getter
    @Setter
    private String entityId;

    @Getter
    @Setter
    private String taxonomy;

    @Getter
    @Setter
    private String attributes;

    @Getter
    @Setter
    private String taxonomyFieldName;

    @Getter
    @Setter
    private String attributesFieldName;
}
