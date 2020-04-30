package com.sommor.bundles.mall.order.model;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.taxonomy.component.attribute.Attributes;
import com.sommor.bundles.taxonomy.component.attribute.AttributesField;
import com.sommor.bundles.user.entity.UserEntity;
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

    @EntityReference(entityName = UserEntity.NAME, fieldBy = "buyerId")
    private Buyer buyer;

    private Long sellerId;

    @EntityReference(entityName = UserEntity.NAME, fieldBy = "sellerId")
    private Seller seller;

    private Long shopId;
    private Long productId;
    private Long skuId;
    private Integer buyQuantity;
    private String productTitle;
    private String productTaxonomy;

    @AttributesField(attributesFieldName = "productAttributes")
    private Attributes productAttributes;

    private Integer status;
    private Integer payStatus;
    private Integer shippingStatus;

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
