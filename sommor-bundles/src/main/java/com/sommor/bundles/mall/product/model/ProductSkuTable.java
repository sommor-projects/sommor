package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.core.model.enricher.EntityReference;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@Setter
@Getter
@EntityReference(entityName = ShopEntity.NAME, fieldBy = "shopId")
@EntityReference(entityName = SkuEntity.NAME, fieldBy = "productId")
public class ProductSkuTable {

    private Long productId;

    private Long skuId;

    private String productTitle;

    private Integer shopId;

    private String shopTitle;

    private Long price;

    public Long getId() {
        return productId;
    }
}
