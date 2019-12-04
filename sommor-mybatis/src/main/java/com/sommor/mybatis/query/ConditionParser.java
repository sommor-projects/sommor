package com.sommor.mybatis.query;

import com.sommor.mybatis.entity.naming.NamingParseStrategy;
import com.sommor.mybatis.query.config.Conditional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class ConditionParser {

    private final static Map<Class, List<ConditionalFieldDefinition>> ConditionalFieldDefinitionsMap = new HashMap<>(128);

    public static List<ConditionalFieldDefinition> parse(Class clazz) {
        List<ConditionalFieldDefinition> fields = ConditionalFieldDefinitionsMap.get(clazz);
        if (null == fields) {
            synchronized (ConditionalFieldDefinitionsMap) {
                fields = ConditionalFieldDefinitionsMap.get(clazz);
                if (null == fields) {
                    fields = new ArrayList<>();

                    for (Field field : clazz.getDeclaredFields()) {
                        Conditional conditional = field.getAnnotation(Conditional.class);
                        if (null != conditional) {
                            String fieldName = field.getName();
                            field.setAccessible(true);
                            ConditionalFieldDefinition ConditionalFieldDefinition = new ConditionalFieldDefinition();
                            ConditionalFieldDefinition.columnName = NamingParseStrategy.INST.parseFieldName2ColumnName(fieldName);
                            ConditionalFieldDefinition.fieldName = fieldName;
                            ConditionalFieldDefinition.field = field;
                            ConditionalFieldDefinition.operator = conditional.operator();

                            fields.add(ConditionalFieldDefinition);
                        }
                    }

                    for (Method method : clazz.getMethods()) {
                        if (method.getModifiers() == Modifier.PUBLIC
                                && method.getParameterCount() == 0
                                && method.getName().startsWith("get")) {


                            Conditional conditional = method.getAnnotation(Conditional.class);
                            if (null != conditional) {
                                String fieldName = method.getName().substring(3);
                                method.setAccessible(true);

                                ConditionalFieldDefinition ConditionalFieldDefinition = new ConditionalFieldDefinition();
                                ConditionalFieldDefinition.columnName = NamingParseStrategy.INST.parseFieldName2ColumnName(fieldName);
                                ConditionalFieldDefinition.fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                                ConditionalFieldDefinition.method = method;
                                ConditionalFieldDefinition.operator = conditional.operator();

                                fields.add(ConditionalFieldDefinition);
                            }
                        }
                    }
                }

                ConditionalFieldDefinitionsMap.put(clazz, fields);
            }
        }

        return fields;
    }
}
