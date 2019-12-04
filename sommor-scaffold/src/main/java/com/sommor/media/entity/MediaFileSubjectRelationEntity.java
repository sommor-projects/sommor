package com.sommor.media.entity;

import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/2
 */
@Table("media_file_subject_relations")
@Getter
@Setter
public class MediaFileSubjectRelationEntity extends BaseEntity {

    @Column
    private Integer mediaFileId;

    @Column
    private String subject;

    @Column
    private Integer subjectId;

    @Column
    private Integer priority;

}
