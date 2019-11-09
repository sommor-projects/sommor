package com.sommor.mybatis.sql.select;

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

    private List<String> expressions = new ArrayList<>();

    public Condition() {
    }

    public Condition(Condition condition) {
        if (null != condition) {
            this.expressions = new ArrayList<>(condition.expressions);
        }
    }

    public Condition and(String columnName, String fieldName, String operator, Object value) {
        this.add("AND", columnName, fieldName, operator, value);
        return this;
    }

    public Condition or(String columnName, String fieldName, String operator, Object value) {
        this.add("OR", columnName, fieldName, operator, value);
        return this;
    }

    private void add(String andOr, String columnName, String fieldName, String operator, Object value) {
        if (! expressions.isEmpty()) {
            expressions.add(andOr);
        }

        if (value instanceof Collection) {
            operator = "in";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("(").append(columnName).append(" ").append(operator).append(" ");
        if ("in".equalsIgnoreCase(operator)) {
            builder.append("(${").append(fieldName).append("})");
        } else {
            builder.append("#{").append(fieldName).append("}");
        }

        builder.append(")");

        expressions.add(builder.toString());
    }

    public String toExpression() {
        if (CollectionUtils.isEmpty(this.expressions)) {
            return "";
        }

        return this.expressions.stream().collect(Collectors.joining(" "));
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
