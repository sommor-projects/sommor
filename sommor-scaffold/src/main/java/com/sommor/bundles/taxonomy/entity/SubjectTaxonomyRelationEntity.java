package com.sommor.bundles.taxonomy.entity;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/22
 */
@Table("taxonomy_subject_relations")
@Getter
@Setter
public class SubjectTaxonomyRelationEntity extends BaseEntity {

    @Column
    private String subject;

    @Column
    private Integer subjectId;

    @Column
    private Integer typeId;

    @Column
    private Integer taxonomyId;

    @Column
    private Integer taxonomyId1;

    @Column
    private Integer taxonomyId2;

    @Column
    private Integer taxonomyId3;

    @Column
    private Integer taxonomyId4;

    @Column
    private Integer taxonomyId5;
}
