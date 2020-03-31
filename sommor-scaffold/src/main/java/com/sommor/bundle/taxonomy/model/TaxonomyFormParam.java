package com.sommor.bundle.taxonomy.model;

import com.sommor.scaffold.param.EntityFormParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */

public class TaxonomyFormParam extends EntityFormParam {

    @Getter @Setter
    private Integer parentId;

}
