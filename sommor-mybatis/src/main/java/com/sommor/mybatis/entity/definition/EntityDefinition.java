package com.sommor.mybatis.entity.definition;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class EntityDefinition {

    @Getter
    @Setter
    private Class entityClass;

    @Getter
    @Setter
    private String tableName;

    @Getter
    @Setter
    private String primaryKey;

    @Getter
    @Setter
    private FieldDefinition primaryField;

    @Getter
    private List<FieldDefinition> fieldDefinitions;

    private Map<String, FieldDefinition> fieldDefinitionMap;

    public void setFieldDefinitions(List<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = fieldDefinitions;
        this.fieldDefinitionMap = fieldDefinitions.stream().collect(Collectors.toMap(p->p.getFieldName(), p->p));
    }

    public FieldDefinition getFieldDefinition(String fieldName) {
        return this.fieldDefinitionMap == null ? null : this.fieldDefinitionMap.get(fieldName);
    }
}
