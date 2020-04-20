package com.sommor.bundles.mall.product.entity;

import com.sommor.bundles.mall.product.model.ProductTypeEnum;
import com.sommor.core.component.configurable.ConfigurableEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Setter
@Getter
@Table(value = "products", entityName = ProductEntity.NAME, autoIncrement = false)
public class ProductEntity extends ConfigurableEntity<Long> {

    public static final String NAME = "product";

    /**
     * @see ProductTypeEnum
     */
    @Column
    private Integer productType;

    @Column
    private Long shopId;

    @Column
    private Long spuId;

    @Column
    private String catalog;

    @Column
    private String qrCode;

    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private String taxonomy;

    @Column
    private String attributes;

    @Column
    private String cover;

    @Column
    private String description;
}
