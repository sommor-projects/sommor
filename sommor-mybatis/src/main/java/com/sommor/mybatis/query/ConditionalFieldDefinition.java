package com.sommor.mybatis.query;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class ConditionalFieldDefinition {
    String columnName;
    String fieldName;
    Field field;
    Method method;
    String operator;

    Object getValue(Object o) {
        try {
            if (null != field) {
                return field.get(o);
            }

            if (null != method) {
                return method.invoke(o);
            }
        } catch (Throwable e) {
            throw new RuntimeException("get value exception, column: "+columnName+", class: " + o.getClass().getName());
        }

        return null;
    }
}
