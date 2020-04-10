package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.core.component.conditional.Conditional;
import com.sommor.core.component.entity.select.EntitySelectField;
import com.sommor.core.component.keywords.KeywordsField;
import com.sommor.core.scaffold.param.EntityQueryParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public class SpuQuery extends EntityQueryParam {

    @Getter
    @Setter
    @KeywordsField(fields = {"title"})
    private String keywords;

    @Getter
    @Setter
    @EntitySelectField(title = "所属店铺", entityName = ShopEntity.NAME)
    @Conditional
    private Integer shopId;
}
