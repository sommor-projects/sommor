package com.sommor.bundles.menu.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-10
 */
@Table("menu_items")
@Getter @Setter
public class MenuItemEntity {

    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String title;

    @Column
    private String icon;

    @Column
    private Integer parentId;

    @Column
    private Integer menuId;

    @Column
    private Integer priority;

    @Column
    private String url;
}
