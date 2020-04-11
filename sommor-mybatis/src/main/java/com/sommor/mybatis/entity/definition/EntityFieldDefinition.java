package com.sommor.mybatis.entity.definition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class EntityFieldDefinition {

    private String fieldName;

    private String columnName;

    private Field field;

    private Method getter;

    private Method setter;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    @Override
    public String toString() {
        return "EntityFieldDefinition{entity='"+field.getDeclaringClass().getSimpleName()+"', " +
                "fieldName='" + fieldName + '\'' +
                ", columnName='" + columnName + '\'' +
                '}';
    }
}
