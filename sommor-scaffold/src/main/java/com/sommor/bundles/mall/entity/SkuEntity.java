package com.sommor.bundles.mall.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import com.sommor.scaffold.entity.configurable.ConfigurableEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Table(value = "product_sku", subject = "sku")
public class SkuEntity extends ConfigurableEntity {

    @Getter
    @Setter
    @Column
    private Integer productId;

    @Getter
    @Setter
    @Column
    private Integer distributedSkuId;

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
