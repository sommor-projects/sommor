package com.sommor.bundle.taxonomy.entity;

import com.sommor.component.configurable.ConfigurableEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Table(value = "posts", entityName = PostEntity.NAME)
@Getter @Setter
public class PostEntity extends ConfigurableEntity {

    public static final String NAME = "post";

    @Column
    private Integer taxonomy;

    @Column
    private String subject;

    @Column
    private String slug;

    @Column
    private String title;

    @Column
    private String subTitle;

    @Column
    private Integer userId;

    @Column
    private String cover;

    @Column
    private String description;
}
