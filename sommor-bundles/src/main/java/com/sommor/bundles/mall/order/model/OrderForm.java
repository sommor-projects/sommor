package com.sommor.bundles.mall.order.model;

import com.sommor.bundles.mall.product.entity.ProductEntity;
import com.sommor.bundles.mall.product.entity.SkuEntity;
import com.sommor.bundles.mall.shop.entity.ShopEntity;
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
@EntityReference(entityName = UserEntity.NAME, fieldBy = "buyerId", target = Buyer.class)
@EntityReference(entityName = ProductEntity.NAME, fieldBy = "productId")
@EntityReference(entityName = SkuEntity.NAME, fieldBy = "skuId")
@EntityReference(entityName = ShopEntity.NAME, fieldBy = "shopId")
@EntityReference(entityName = UserEntity.NAME, fieldBy = "shopUserId", target = Seller.class)
public class OrderForm {

    @NotNull
    @EntitySelectField(entityName = ProductEntity.NAME, title = "商品")
    private Long productId;

    private Long skuId;

    @NotNull
    @InputField(title = "购买件数")
    private Integer buyQuantity = 1;
}
