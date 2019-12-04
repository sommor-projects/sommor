package com.sommor.mybatis.sql.select;

import com.sommor.mybatis.sql.field.type.Array;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class Condition {

    private List<ConditionExpression> expressions = new ArrayList<>();

    @Setter
    private String tableAlias;

    @Getter
    private class ConditionExpression {
        private String andOr;
        private String fieldName;
        private String columnName;
        private String operator;

        public ConditionExpression(String andOr, String fieldName, String columnName, String operator) {
            this.andOr = andOr;
            this.fieldName = fieldName;
            this.columnName = columnName;
            this.operator = operator;
        }
    }

    public Condition() {
    }

    public Condition(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    @Setter
    private String entityPrefix;

    public Condition and(String columnName, String fieldName, String operator, Object value) {
        this.add("AND", columnName, fieldName, operator, value);
        return this;
    }

    public Condition or(String columnName, String fieldName, String operator, Object value) {
        this.add("OR", columnName, fieldName, operator, value);
        return this;
    }

    private void add(String andOr, String columnName, String fieldName, String operator, Object value) {
        if (expressions.isEmpty()) {
            andOr = null;
        }

        if (value instanceof Array && ((Array) value).size() > 1) {
            operator = "in";
        }

        this.expressions.add(
                new ConditionExpression(andOr, fieldName, columnName, operator)
        );
    }

    public String toExpression() {
        if (CollectionUtils.isEmpty(this.expressions)) {
            return "";
        }

        return this.expressions.stream().map( e -> {
            StringBuilder builder = new StringBuilder();
            if (null != e.getAndOr()) {
                builder.append(e.getAndOr()).append(" ");
            }
            builder.append("(");
            if (null != this.tableAlias) {
                builder.append(this.tableAlias).append(".");
            }

            builder.append(e.getColumnName()).append(" ").append(e.getOperator()).append(" ");

            String fieldName = e.getFieldName();
            if (null != this.entityPrefix) {
                fieldName = this.entityPrefix + "." + fieldName;
            }

            if ("in".equalsIgnoreCase(e.getOperator())) {
                builder.append("(${").append(fieldName).append("})");
            } else {
                builder.append("#{").append(fieldName).append("}");
            }
            builder.append(")");

            return builder.toString();
        }).collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        String expression = toExpression();
        if (! StringUtils.isEmpty(expression)) {
            expression = "WHERE " + expression;
        }

        return expression;
    }
}
