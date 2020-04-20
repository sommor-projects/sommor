package com.sommor.bundles.mall.shop.entity;

import com.sommor.core.component.configurable.ConfigurableEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import com.sommor.mybatis.sql.field.type.Location;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Table(value = "shops", entityName = ShopEntity.NAME, autoIncrement = false)
@Setter
@Getter
public class ShopEntity extends ConfigurableEntity<Long> {

    public static final String NAME = "shop";

    @Column
    private Long userId;

    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private String taxonomy;

    @Column
    private String attributes;

    @Column
    private String logo;

    @Column
    private String webSite;

    @Column
    private String openHours;

    @Column
    private String description;

    @Column
    private Location location;

    @Column
    private Integer divisionId;


    @Column
    private String address;
}
