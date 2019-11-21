package com.sommor.mybatis.entity.definition;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import com.sommor.mybatis.entity.naming.NamingParseStrategy;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class EntityDefinitionFactory {

    private static final Map<Class, EntityDefinition> ENTITY_DEFINITION_MAP = new HashMap<>(128);

    public static EntityDefinition getDefinition(Class entityClass) {
        EntityDefinition ed = ENTITY_DEFINITION_MAP.get(entityClass);
        if (null == ed) {
            synchronized (ENTITY_DEFINITION_MAP) {
                ed = ENTITY_DEFINITION_MAP.get(entityClass);
                if (null == ed) {
                    ed = new EntityDefinition();
                    ed.setEntityClass(entityClass);
                    Table table = (Table) entityClass.getAnnotation(Table.class);
                    if (null == table) {
                        String className = entityClass.getSimpleName();
                        ed.setTableName(NamingParseStrategy.INST.parseFieldName2ColumnName(className));
                        ed.setPrimaryKey("id");
                    } else {
                        String tableName = table.value();
                        if (StringUtils.isBlank(tableName)) {
                            tableName = NamingParseStrategy.INST.parseFieldName2ColumnName(entityClass.getSimpleName());
                        }
                        ed.setTableName(tableName);
                        ed.setPrimaryKey(table.primaryKey());
                    }

                    List<FieldDefinition> fieldDefinitions = new ArrayList<>();
                    Class clazz = entityClass;

                    while (null != clazz) {
                        for (Field field : clazz.getDeclaredFields()) {
                            Column column = field.getAnnotation(Column.class);
                            if (null != column) {
                                FieldDefinition fieldDefinition = parseFieldDefinition(clazz, field, column.value());

                                if (fieldDefinition.getColumnName().equals(ed.getPrimaryKey())) {
                                    ed.setPrimaryField(fieldDefinition);
                                }

                                fieldDefinitions.add(fieldDefinition);
                            }
                        }
                        clazz = clazz.getSuperclass();
                    }

                    ed.setFieldDefinitions(fieldDefinitions);

                    ENTITY_DEFINITION_MAP.put(entityClass, ed);
                }
            }
        }

        return ed;
    }

    private static FieldDefinition parseFieldDefinition(Class entityClass, Field field, String columnName) {
        FieldDefinition fieldDefinition = new FieldDefinition();
        fieldDefinition.setFieldName(field.getName());
        fieldDefinition.setField(field);

        if (StringUtils.isBlank(columnName)) {
            columnName = NamingParseStrategy.INST.parseFieldName2ColumnName(field.getName());
        }

        fieldDefinition.setColumnName(columnName);

        String methodNameSuffix = field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);

        String getMethodName = "get" + methodNameSuffix;
        fieldDefinition.setFieldGetMethod(parseMethod(getMethodName, entityClass));

        String setMethodName = "set" + methodNameSuffix;
        fieldDefinition.setFieldSetMethod(parseMethod(setMethodName, entityClass, field.getType()));

        return fieldDefinition;
    }

    private static Method parseMethod(String methodName, Class clazz, Class... argTypes) {
        try {
            Method method = clazz.getMethod(methodName, argTypes);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
