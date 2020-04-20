package com.sommor.bundles.mall.order.entity;

import com.sommor.core.component.configurable.ConfigurableEntity;
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

    private Long userId;
    private Long sellerId;
    private Long shopId;
    private Long productId;
    private Long skuId;
    private Integer buyQuantity;
    private String productTitle;
    private String productTaxonomy;
    private String productAttributes;
    private Integer status;
    private Integer payStatus;
    private Integer shippingStatus;
    private Integer createTime;
    private Integer payTime;
    private Integer updateTime;

}
