package com.sommor.taxonomy.param;

import com.sommor.mybatis.query.EntityQueryParam;
import com.sommor.mybatis.query.config.Conditional;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/25
 */
public class TaxonomyQueryParam extends EntityQueryParam {

    @Getter @Setter
    @Conditional
    private Integer parentId;

    @Setter
    @Getter
    private String slug;

    @Setter
    @Getter
    private Integer typeId;
}
