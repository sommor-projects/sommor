package com.sommor.mybatis.sql.select;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class Condition {

    @Getter
    private List<ConditionExpression> expressions = new ArrayList<>();

    public void setColumnPrefix(String columnPrefix) {
        for (ConditionExpression expression : expressions) {
            if (null == expression.getColumnPrefix()) {
                expression.columnPrefix(columnPrefix);
            }
        }
    }

    public void setFieldPrefix(String fieldPrefix) {
        for (ConditionExpression expression : expressions) {
            if (null == expression.getFieldPrefix()) {
                expression.fieldPrefix(fieldPrefix);
            }
        }
    }

    public Condition add(ConditionExpression expression) {
        this.expressions.add(expression);

        return this;
    }

    public Condition and(String fieldName, Object value) {
        return and(fieldName, null, null, value);
    }

    public Condition and(String fieldName, ConditionOperator operator, Object value) {
        return and(fieldName, null, operator, value);
    }

    public Condition andLike(String fieldName, Object value) {
        return and(fieldName, null, ConditionOperator.LIKE, value);
    }

    public Condition and(String fieldName, String columnName, Object value) {
        return and(fieldName, columnName, null, value);
    }

    public Condition and(String fieldName, String columnName, ConditionOperator operator, Object value) {
        ConditionExpression expression = ConditionExpression.of("AND", fieldName, columnName, operator, value);
        this.add(expression);

        return this;
    }

    public Condition or(String fieldName, Object value) {
        return or(fieldName, null, null, value);
    }

    public Condition orLike(String fieldName, Object value) {
        return or(fieldName, null, ConditionOperator.LIKE, value);
    }

    public Condition or(String fieldName, String columnName, Object value) {
        return or(fieldName, columnName, null, value);
    }

    public Condition or(String fieldName, String columnName, ConditionOperator operator, Object value) {
        ConditionExpression expression = ConditionExpression.of("OR", fieldName, columnName, operator, value);
        this.add(expression);

        return this;
    }

    public String toExpression() {
        if (CollectionUtils.isEmpty(expressions)) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("(");

        for (ConditionExpression expression : expressions) {
            if (builder.length() > 1) {
                builder.append(" ")
                        .append(expression.getAndOr())
                        .append(" ");
            }

            if (null != expression.getColumnPrefix()) {
                builder.append("`")
                        .append(expression.getColumnPrefix())
                        .append("`")
                        .append(".");
            }

            builder.append(expression.getColumnName())
                    .append(" ")
                    .append(expression.getOperator())
                    .append(" ");

            String fieldName = expression.getFieldName();
            if (null != expression.getFieldPrefix()) {
                fieldName = expression.getFieldPrefix() + "." + fieldName;
            }

            if ("in".equalsIgnoreCase(expression.getOperator())) {
                builder.append("(${").append(fieldName).append("})");
            } else if ("like".equalsIgnoreCase(expression.getOperator())) {
                builder.append("CONCAT('%',#{").append(fieldName).append("},'%')");
            } else {
                builder.append("#{").append(fieldName).append("}");
            }
        }

        builder.append(")");

        return builder.toString();
    }

    @Override
    public String toString() {
        return toExpression();
    }
}
