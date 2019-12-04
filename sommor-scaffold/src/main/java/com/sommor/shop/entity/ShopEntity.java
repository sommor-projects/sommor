package com.sommor.shop.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import com.sommor.mybatis.sql.field.type.Location;
import com.sommor.taxonomy.entity.SubjectBasedEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Table("shops")
@Setter
@Getter
public class ShopEntity extends SubjectBasedEntity {

    @Column
    private Integer userId;

    @Column
    private Integer countryId;

    @Column
    private String name;

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
    private String cover;

    @Column
    private String address;

    @Override
    public String subject() {
        return "shop";
    }
}
