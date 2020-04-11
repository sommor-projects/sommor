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
    private String subjectName;

    @Getter
    @Setter
    private String primaryKey;

    @Getter
    @Setter
    private boolean autoIncrement;

    @Getter
    @Setter
    private EntityFieldDefinition primaryField;

    @Getter
    private List<EntityFieldDefinition> fieldDefinitions;

    private Map<String, EntityFieldDefinition> fieldDefinitionMap;

    public String getTableAlias() {
        return this.getSubjectName() == null ? this.getTableName() : this.getSubjectName();
    }

    public void setFieldDefinitions(List<EntityFieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = fieldDefinitions;
        this.fieldDefinitionMap = fieldDefinitions.stream().collect(Collectors.toMap(p->p.getFieldName(), p->p));
    }

    public EntityFieldDefinition getFieldDefinition(String fieldName) {
        return this.fieldDefinitionMap == null ? null : this.fieldDefinitionMap.get(fieldName);
    }
}
