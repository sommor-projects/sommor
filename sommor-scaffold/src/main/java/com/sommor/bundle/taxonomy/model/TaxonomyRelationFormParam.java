package com.sommor.bundle.taxonomy.model;

import com.sommor.bundle.taxonomy.component.convert.TaxonomyConvertField;
import com.sommor.scaffold.param.EntityFormParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class TaxonomyRelationFormParam extends EntityFormParam {

    @Getter
    @Setter
    private String taxonomy;

    @Getter
    @Setter
    @TaxonomyConvertField
    private Integer taxonomyId;

}
