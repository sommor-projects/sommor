package com.sommor.mybatis.sql.select;


import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuyu
 * @since 2019/2/11
 */
public class OrderBy {

    private List<String> expressions = new ArrayList<>();

    public OrderBy(String columnName, OrderType orderType) {
        this.add(columnName, orderType);
    }

    public OrderBy add(String columnName, OrderType orderType) {
        this.expressions.add(columnName + " " + orderType);
        return this;
    }

    public String toExpression() {
        if (CollectionUtils.isEmpty(this.expressions)) {
            return "";
        }

        return this.expressions.stream()
            .collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        String expression = toExpression();

        if (! StringUtils.isEmpty(expression)) {
            expression = "ORDER BY " + expression;
        }

        return expression;
    }
}
