package com.sommor.bundles.mall.order.model;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.product.model.Product;
import com.sommor.bundles.mall.product.model.Sku;
import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.taxonomy.component.attribute.Attributes;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.core.component.entity.select.EntitySelectField;
import com.sommor.core.component.form.field.InputField;
import com.sommor.core.model.enricher.EntityReference;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/30
 */
@Getter
@Setter
@EntityReference(entity = UserEntity.NAME, byField = "buyerId", target = Buyer.class)
@EntityReference(entity = ProductEntity.NAME, byField = "productId", target = Product.class)
@EntityReference(entity = SkuEntity.NAME, byField = "skuId", target = Sku.class)
@EntityReference(entity = ShopEntity.NAME, byField = "shopId")
@EntityReference(entity = UserEntity.NAME, byField = "shopUserId", target = Seller.class)
public class OrderForm {

    private Long refId;

    @NotNull
    @EntitySelectField(entityName = ProductEntity.NAME, title = "商品")
    private Long productId;

    private Long skuId;

    @NotNull
    @InputField(title = "购买件数")
    private Integer buyQuantity = 1;

    private Attributes attributes;
}
