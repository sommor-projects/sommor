package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.taxonomy.model.EntityTaxonomyQueryParam;
import com.sommor.core.component.conditional.Conditional;
import com.sommor.core.component.keywords.KeywordsField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
public class SpuQueryParam extends EntityTaxonomyQueryParam {

    @Getter
    @Setter
    @Conditional
    private Integer shopId;

    @Getter
    @Setter
    @KeywordsField(fields = {"title"})
    private String keywords;

    public SpuQueryParam() {
        super("spu");
    }
}
