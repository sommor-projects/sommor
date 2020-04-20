package com.sommor.bundles.mall.product.entity;

import com.sommor.core.component.configurable.ConfigurableEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Table(value = "product_sku", entityName = SkuEntity.NAME, autoIncrement = false)
public class SkuEntity extends ConfigurableEntity<Long> {

    public static final String NAME = "sku";

    @Getter
    @Setter
    @Column
    private Long shopId;

    @Getter
    @Setter
    @Column
    private Long productId;

    @Getter
    @Setter
    @Column
    private Integer productType;

    @Getter
    @Setter
    @Column
    private String taxonomy;

    @Getter
    @Setter
    @Column
    private Long distributedSkuId;

    @Getter
    @Setter
    @Column
    private String title;

    @Getter
    @Setter
    @Column
    private Integer price;

    @Getter
    @Setter
    @Column
    private Integer costPrice;

    @Getter
    @Setter
    private String currency;

    @Getter
    @Setter
    @Column
    private Integer inventory;

    @Getter
    @Setter
    @Column
    private Integer warningInventory;
}
