package com.sommor.bundles.taxonomy.model;

import com.sommor.core.scaffold.param.EntityFormParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class EntityTaxonomyFormParam extends EntityFormParam {

    @Getter
    @Setter
    private String taxonomy;
}
