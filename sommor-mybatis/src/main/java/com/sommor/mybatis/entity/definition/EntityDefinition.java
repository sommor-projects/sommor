package com.sommor.mybatis.entity.definition;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
@Getter
@Setter
public class EntityDefinition {

    private Class entityClass;

    private String tableName;

    private String primaryKey;

    private FieldDefinition primaryField;

    private List<FieldDefinition> fieldDefinitions;
}
