package com.sommor.taxonomy.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Table("items")
@Getter @Setter
public class ItemEntity {

    @Column
    private Integer id;

    @Column
    private Integer taxonomyId;

    @Column
    private String slug;

    @Column
    private String name;

    @Column
    private String title;

    @Column
    private Integer authorId;

    @Column
    private String cover;

    @Column
    private String description;

    @Column
    private Integer updateTime;

    @Column
    private Integer createTime;
}
