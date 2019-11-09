package com.sommor.taxonomy.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Table("attributes")
@Getter @Setter
public class AttributeEntity {

    @Column
    private Integer id;

    @Column
    private String slug;

    @Column
    private String name;

    @Column
    private String title;
}
