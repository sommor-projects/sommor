package com.sommor.bundles.mall.order.model;

import com.sommor.bundles.mall.order.entity.OrderEntity;
import com.sommor.bundles.taxonomy.component.attribute.Attributes;
import com.sommor.bundles.taxonomy.component.attribute.AttributesField;
import com.sommor.core.model.Model;
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
    private Long userId;
    private Long sellerId;
    private Long shopId;
    private Long productId;
    private Long skuId;
    private Integer buyQuantity;
    private String productTitle;
    private String productTaxonomy;

    @AttributesField
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
