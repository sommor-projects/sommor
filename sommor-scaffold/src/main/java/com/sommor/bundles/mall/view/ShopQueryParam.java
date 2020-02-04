package com.sommor.bundles.mall.view;

import com.sommor.bundles.taxonomy.view.SubjectTaxonomyQueryParam;
import com.sommor.scaffold.fields.keywords.KeywordsField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
public class ShopQueryParam extends SubjectTaxonomyQueryParam {

    @Getter
    @Setter
    @KeywordsField(fields = {"title"})
    private String keywords;

    public ShopQueryParam() {
        super("shop");
    }
}
