package com.sommor.bundle.mall.product.model;

import com.sommor.bundle.taxonomy.model.TaxonomyRelationQueryParam;
import com.sommor.component.conditional.Conditional;
import com.sommor.component.keywords.KeywordsField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
public class SpuQueryParam extends TaxonomyRelationQueryParam {

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
