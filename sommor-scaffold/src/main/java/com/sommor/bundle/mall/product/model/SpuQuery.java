package com.sommor.bundle.mall.product.model;

import com.sommor.bundle.mall.shop.entity.ShopEntity;
import com.sommor.component.conditional.Conditional;
import com.sommor.component.entity.select.EntitySelectField;
import com.sommor.component.keywords.KeywordsField;
import com.sommor.scaffold.param.EntityQueryParam;
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
