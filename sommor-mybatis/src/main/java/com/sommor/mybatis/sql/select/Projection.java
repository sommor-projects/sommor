package com.sommor.mybatis.sql.select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class Projection {

    private List<String> expressions = new ArrayList<>();

    public Projection all(String tableName) {
        String allExpression = "*";
        if (null != tableName) {
            allExpression = tableName + "." + allExpression;
        }
        this.expressions.add(allExpression);

        return this;
    }

    public Projection all() {
        return all(null);
    }

    public Projection columns(String... columns) {
        this.expressions.add(Arrays.asList(columns).stream().collect(Collectors.joining(", ")));

        return this;
    }

    public Projection count() {
        this.expressions.add("COUNT(1)");

        return this;
    }

    @Override
    public String toString() {
        return expressions.stream().collect(Collectors.joining(", "));
    }
}
