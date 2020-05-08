package com.sommor.mybatis.entity.definition;

import com.sommor.mybatis.entity.config.Column;
import com.sommor.mybatis.entity.config.Table;
import com.sommor.mybatis.entity.naming.NamingParseStrategy;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class EntityManager {

    private static final Map<Class, EntityDefinition> ENTITY_DEFINITION_MAP = new HashMap<>(128);

    private static final Map<String, EntityDefinition> ENTITY_DEFINITION_MAP_BY_SUBJECT = new HashMap<>(128);

    public static Collection<EntityDefinition> all() {
        return ENTITY_DEFINITION_MAP.values();
    }

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
                        ed.setAutoIncrement(table.autoIncrement());
                        ed.setSoftDelete(table.softDelete());
                        ed.setSubjectName(table.entity().isEmpty() ? null : table.entity());
                    }

                    List<EntityFieldDefinition> fieldDefinitions = new ArrayList<>();
                    Class clazz = entityClass;

                    while (null != clazz) {
                        for (Field field : clazz.getDeclaredFields()) {
                            Column column = field.getAnnotation(Column.class);
                            if (null != column) {
                                EntityFieldDefinition fieldDefinition = parseFieldDefinition(clazz, field, column.value());

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

                    if (StringUtils.isNoneBlank(ed.getSubjectName())) {
                        ENTITY_DEFINITION_MAP_BY_SUBJECT.put(ed.getSubjectName(), ed);
                    }
                }
            }
        }

        return ed;
    }

    public static EntityDefinition getDefinitionBySubject(String subject) {
        return ENTITY_DEFINITION_MAP_BY_SUBJECT.get(subject);
    }

    private static EntityFieldDefinition parseFieldDefinition(Class entityClass, Field field, String columnName) {
        EntityFieldDefinition fieldDefinition = new EntityFieldDefinition();
        fieldDefinition.setFieldName(field.getName());
        fieldDefinition.setField(field);

        if (StringUtils.isBlank(columnName)) {
            columnName = NamingParseStrategy.INST.parseFieldName2ColumnName(field.getName());
        }

        fieldDefinition.setColumnName(columnName);

        String methodNameSuffix = field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);

        String getMethodName = "get" + methodNameSuffix;
        fieldDefinition.setGetter(parseMethod(getMethodName, entityClass));

        String setMethodName = "set" + methodNameSuffix;
        fieldDefinition.setSetter(parseMethod(setMethodName, entityClass, field.getType()));

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
