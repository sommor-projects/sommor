package com.sommor.taxonomy.param;

import com.sommor.scaffold.param.EntityInfoParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/25
 */
@Setter
@Getter
public class TaxonomyInfoParam extends EntityInfoParam {

    private Integer parentId;

    private Integer typeId;

    private String type;

    private String subject;

    private String slug;

}
