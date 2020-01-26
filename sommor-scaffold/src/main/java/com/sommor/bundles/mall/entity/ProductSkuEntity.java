package com.sommor.bundles.mall.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import com.sommor.scaffold.entity.timed.TimedEntity;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/20
 */
@Table(value = "product_sku", subject = "productSku")
public class ProductSkuEntity extends TimedEntity {

    @Column
    private Integer spuId;

    @Column
    private Integer userId;

    @Column
    private Integer shopId;

    /**
     * 条型码
     */
    @Column
    private String barCode;

    /**
     * 二维码
     */
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
