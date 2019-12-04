package com.sommor.shop.entity;

import com.sommor.mybatis.entity.ConfigurableEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Table("products")
@Getter
@Setter
public class ProductEntity extends ConfigurableEntity {

    @Column
    private Integer itemId;

    @Column
    private Integer userId;

    @Column
    private Integer shopId;

    @Column
    private String barCode;

    @Column
    private Integer qrCodeId;

    @Column
    private Integer price;

    @Column
    private Integer costPrice;

    @Column
    private Integer inventory;

    @Column
    private Integer warningInventory;
}
