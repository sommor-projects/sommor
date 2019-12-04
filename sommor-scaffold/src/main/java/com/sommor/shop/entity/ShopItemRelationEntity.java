package com.sommor.shop.entity;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Table("shop_items")
@Setter
@Getter
public class ShopItemRelationEntity extends BaseEntity {

    @Column
    private Integer itemId;

    @Column
    private Integer shopId;

    /**
     * 被分销的店铺ID
     */
    @Column
    private Integer distributedShopId;

}
