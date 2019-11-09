package com.sommor.mybatis.entity.definition;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class EntityDefinition {

    private String tableName;

    private String primaryKey;

    private FieldDefinition primaryField;

    private List<FieldDefinition> fieldDefinitions;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public FieldDefinition getPrimaryField() {
        return primaryField;
    }

    public void setPrimaryField(FieldDefinition primaryField) {
        this.primaryField = primaryField;
    }

    public List<FieldDefinition> getFieldDefinitions() {
        return fieldDefinitions;
    }

    public void setFieldDefinitions(List<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = fieldDefinitions;
    }
}
