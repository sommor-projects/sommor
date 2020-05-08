package com.sommor.bundles.mall.order.model;

import com.sommor.core.component.status.StatusField;
import com.sommor.core.component.status.StatusVO;
import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.mall.shop.entity.ShopEntity;
import com.sommor.bundles.mall.shop.model.Shop;
import com.sommor.bundles.taxonomy.component.attribute.Attributes;
import com.sommor.bundles.taxonomy.component.attribute.AttributesField;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.core.component.currency.MoneyAmountField;
import com.sommor.core.model.Model;
import com.sommor.core.model.enricher.EntityReference;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Getter
@Setter
public class Order {

    private Long id;
    private Long buyerId;

    @EntityReference(entity = UserEntity.NAME, byField = "buyerId")
    private Buyer buyer;

    private Long sellerId;

    @EntityReference(entity = UserEntity.NAME, byField = "sellerId")
    private Seller seller;

    private Long shopId;

    @EntityReference(entity = ShopEntity.NAME, byField = "shopId")
    private Shop shop;

    private Long productId;
    private Long skuId;
    private Integer buyQuantity;

    @MoneyAmountField
    private String amount;

    private String productTitle;
    private String productTaxonomy;

    @AttributesField(attributesFieldName = "productAttributes")
    private Attributes productAttributes;

    @StatusField
    private StatusVO orderStatus;

    @StatusField
    private StatusVO payStatus;

    @StatusField
    private StatusVO shippingStatus;

    private Integer createTime;
    private Integer payTime;
    private Integer shipTime;
    private Integer updateTime;

    public static Order of(OrderEntity orderEntity) {
        Order order = new Order();
        Model.fillData(order, orderEntity);
        return order;
    }
}
