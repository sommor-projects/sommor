package com.sommor.taxonomy.entity;

import com.sommor.mybatis.entity.ConfigurableEntity;
import com.sommor.mybatis.entity.config.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/3
 */

abstract public class SubjectBasedEntity extends ConfigurableEntity {

    @Column
    @Getter @Setter
    private Integer typeId;

    abstract public String subject();
}
