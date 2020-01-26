package com.sommor.mybatis.sql.select;

import com.sommor.mybatis.entity.naming.NamingParseStrategy;
import com.sommor.mybatis.sql.field.type.Array;
import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
@Getter
public class ConditionExpression {

    private String andOr;

    private String fieldName;

    private String columnName;

    private String operator;

    private String columnPrefix;

    private String fieldPrefix;

    private Object value;

    public static ConditionExpression of(String andOr, String fieldName, String columnName, ConditionOperator operator, Object value) {
        ConditionExpression expression = new ConditionExpression();

        if (null == operator) {
            if (value instanceof Array) {
                operator = ConditionOperator.IN;
            } else {
                operator = ConditionOperator.EQ;
            }
        }

        if (null == columnName) {
            columnName = NamingParseStrategy.INST.parseFieldName2ColumnName(fieldName);
        } else {
            if (columnName.indexOf(".") > 0) {
                String[] a = columnName.split("\\.");
                expression.columnPrefix = a[0];
                columnName = a[1];
            }
        }

        if (fieldName.indexOf(".") > 0) {
            String[] a = fieldName.split("\\.");
            expression.fieldPrefix = a[0];
            fieldName = a[1];
        }

        expression.andOr = andOr;
        expression.fieldName = fieldName;
        expression.columnName = columnName;
        expression.operator = operator.getOperator();
        expression.value = value;

        return expression;
    }

    public ConditionExpression columnPrefix(String columnPrefix) {
        this.columnPrefix = columnPrefix;
        return this;
    }

    public ConditionExpression fieldPrefix(String fieldPrefix) {
        this.fieldPrefix = fieldPrefix;
        return this;
    }
}
