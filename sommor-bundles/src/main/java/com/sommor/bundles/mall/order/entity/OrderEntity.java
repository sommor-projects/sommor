package com.sommor.bundles.mall.order.entity;

import com.sommor.core.component.configurable.ConfigurableEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/20
 */
@Getter
@Setter
@Table(value = "orders", entity = OrderEntity.NAME, autoIncrement = false)
public class OrderEntity extends ConfigurableEntity<Long> {

    public static final String NAME = "order";

    @Column
    private Long refId;

    @Column
    private Long buyerId;

    @Column
    private Long sellerId;

    @Column
    private Long shopId;

    @Column
    private Long productId;

    @Column
    private Long skuId;

    @Column
    private Integer buyQuantity;

    @Column
    private Long amount;

    @Column
    private String currency;

    @Column
    private String amountDetails;

    @Column
    private String productTitle;

    @Column
    private String taxonomy;

    @Column
    private String attributes;

    @Column
    private Integer status;

    @Column
    private Integer payStatus;

    @Column
    private Integer shippingStatus;

    @Column
    private Long payTime;

    @Column
    private Long shipTime;
}
