package com.sommor.taxonomy.entity;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Table("taxonomies")
@Getter @Setter
public class TaxonomyEntity {

    @Column
    private Integer id;

    @Column
    private String slug;

    @Column
    private String name;

    @Column
    private String title;

    /**
     * 顶层分类ID，最顶层分类该值为0
     */
    @Column
    private Integer rootId;

    /**
     * 父分类ID，最顶层的分类该值为0
     */
    @Column
    private Integer parentId;

    /**
     * 关联的分类
     */
    @Column
    private List<Integer> relatedTaxonomies;

    /**
     * 关联的属性
     */
    @Column
    private List<Integer> relatedAttributes;
}
