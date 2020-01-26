package com.sommor.bundles.mall.view;

import com.sommor.scaffold.fields.keywords.KeywordsField;
import com.sommor.scaffold.param.EntitySearchParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
public class ShopQueryParam extends EntitySearchParam {

    @Getter
    @Setter
    @KeywordsField(fields = {"title"})
    private String keywords;
}
