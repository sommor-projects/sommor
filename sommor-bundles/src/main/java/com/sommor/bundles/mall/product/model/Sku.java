package com.sommor.bundles.mall.product.model;

import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.taxonomy.component.attribute.Attributes;
import com.sommor.bundles.taxonomy.component.attribute.AttributesField;
import com.sommor.core.model.Model;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
@Getter
@Setter
public class Sku {
    private Long skuId;

    private Long shopId;

    private Long productId;

    private Integer productType;

    private String taxonomy;

    @AttributesField
    private Attributes attributes;

    private Long distributedSkuId;

    private String title;

    private Long price;

    private Long costPrice;

    private String currency;

    private Integer inventory;

    private Integer warningInventory;

    public static Sku of(SkuEntity entity) {
        Sku sku = new Sku();
        Model.fillData(sku, entity);
        return sku;
    }
}
