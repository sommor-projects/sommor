package com.sommor.bundle.mall.shop.model;

import com.sommor.bundle.taxonomy.model.TaxonomyRelationQueryParam;
import com.sommor.component.keywords.KeywordsField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
public class ShopQueryParam extends TaxonomyRelationQueryParam {

    @Getter
    @Setter
    @KeywordsField(fields = {"title", "subTitle"})
    private String keywords;

    public ShopQueryParam() {
        super("shop");
    }
}
