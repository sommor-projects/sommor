package com.sommor.bundle.taxonomy.component.relation;

import com.sommor.view.field.FieldsetConfig;
import com.sommor.view.field.FieldsetView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/14
 */
public class TaxonomyAttributeConfig extends FieldsetConfig<FieldsetView> {

    @Getter
    @Setter
    private String entityName;

    @Getter
    @Setter
    private Integer entityId;

    @Getter
    @Setter
    private String taxonomy;

    @Getter
    @Setter
    private String taxonomyFieldName;
}
