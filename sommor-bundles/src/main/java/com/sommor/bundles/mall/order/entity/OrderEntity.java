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
@Table(value = "orders", entityName = "order", autoIncrement = false)
public class OrderEntity extends ConfigurableEntity<Long> {

    public static final Integer PAY_STATUS_UNPAID = 0;
    public static final Integer PAY_STATUS_PAID = 1;
    public static final Integer PAY_STATUS_REFUND = 2;

    public static final Integer SHIPPING_STATUS_UNSHIPPED = 0;
    public static final Integer SHIPPING_STATUS_SHIPPED = 1;

    public static final Integer STATUS_CREATE = 0;
    public static final Integer STATUS_CANCEL = 1;
    public static final Integer STATUS_SUCCESS = 2;
    public static final Integer STATUS_CLOSED = 3;

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
    private String productTaxonomy;

    @Column
    private String productAttributes;

    @Column
    private String skuAttributes;

    @Column
    private Integer status;

    @Column
    private Integer payStatus;

    @Column
    private Integer shippingStatus;

    @Column
    private Integer payTime;

    @Column
    private Integer shipTime;
}
