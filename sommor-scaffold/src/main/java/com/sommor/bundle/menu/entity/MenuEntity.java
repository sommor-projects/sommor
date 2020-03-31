package com.sommor.bundle.menu.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-10
 */
@Table("menus")
@Getter @Setter
public class MenuEntity {

    @Column
    private Integer id;

    @Column
    private String slug;

    @Column
    private String title;

}
