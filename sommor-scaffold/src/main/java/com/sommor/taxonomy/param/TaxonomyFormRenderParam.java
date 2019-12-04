package com.sommor.taxonomy.param;

import com.sommor.scaffold.param.EntityFormRenderParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
@Getter
@Setter
public class TaxonomyFormRenderParam extends EntityFormRenderParam {

    private Integer parentId;

}
