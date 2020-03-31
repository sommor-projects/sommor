package com.sommor.bundle.taxonomy.model;

import com.sommor.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/22
 */
@Getter
@Setter
public class TaxonomyPostRelation extends BaseEntity {

    private String name;

    private String title;

    private Integer priority;

    private Integer parentId;

    private Integer postCount;

    private Integer relatedPostCount;
}
