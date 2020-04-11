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
@Table(value = "taxonomy_subject_relations", entityName = "taxonomy_subject")
@Getter
@Setter
public class SubjectTaxonomyRelationEntity extends BaseEntity {

    @Column
    private String subject;

    @Column
    private Long subjectId;

    @Column
    private Long typeId;

    @Column
    private Long taxonomyId;

    @Column
    private Long taxonomyId1;

    @Column
    private Long taxonomyId2;

    @Column
    private Long taxonomyId3;

    @Column
    private Long taxonomyId4;

    @Column
    private Long taxonomyId5;
}
