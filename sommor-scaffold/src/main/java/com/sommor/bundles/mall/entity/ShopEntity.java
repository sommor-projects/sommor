package com.sommor.bundles.mall.entity;

import com.sommor.scaffold.entity.configurable.ConfigurableEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import com.sommor.mybatis.sql.field.type.Location;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Table(value = "shops", subject = "shop")
@Setter
@Getter
public class ShopEntity extends ConfigurableEntity {

    @Column
    private Integer userId;

    @Column
    private String title;

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
    private Integer taxonomy;

    @Column
    private String address;
}
