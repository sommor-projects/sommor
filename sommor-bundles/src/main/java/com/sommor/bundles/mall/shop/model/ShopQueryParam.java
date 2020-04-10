package com.sommor.bundles.mall.shop.model;

import com.sommor.bundles.taxonomy.model.EntityTaxonomyQueryParam;
import com.sommor.core.component.keywords.KeywordsField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
public class ShopQueryParam extends EntityTaxonomyQueryParam {

    @Getter
    @Setter
    @KeywordsField(fields = {"title", "subTitle"})
    private String keywords;

    public ShopQueryParam() {
        super("shop");
    }
}
