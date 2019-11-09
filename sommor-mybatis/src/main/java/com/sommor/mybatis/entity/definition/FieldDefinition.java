package com.sommor.mybatis.entity.definition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class FieldDefinition {

    private String fieldName;

    private String columnName;

    private Field field;

    private Method fieldGetMethod;

    private Method fieldSetMethod;

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

    public Method getFieldGetMethod() {
        return fieldGetMethod;
    }

    public void setFieldGetMethod(Method fieldGetMethod) {
        this.fieldGetMethod = fieldGetMethod;
    }

    public Method getFieldSetMethod() {
        return fieldSetMethod;
    }

    public void setFieldSetMethod(Method fieldSetMethod) {
        this.fieldSetMethod = fieldSetMethod;
    }
}
