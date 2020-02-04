package com.sommor.mybatis.sql.select;

import lombok.Setter;

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

    @Setter
    private String tableAlias;

    public Projection() {
    }

    public Projection(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public Projection all() {
        String allExpression = "*";
        if (null != this.tableAlias) {
            allExpression = this.tableAlias + "." + allExpression;
        }
        this.expressions.add(allExpression);

        return this;
    }

    public Projection columns(String column) {
        this.expressions.add(column);
        return this;
    }

    public Projection columns(String... columns) {
        this.expressions.add(String.join(", ", columns));
        return this;
    }

    public Projection count() {
        this.expressions.add("COUNT(1)");
        return this;
    }

    @Override
    public String toString() {
        return expressions.stream()
                .map(column -> {
                    if (! column.contains(".") && null != this.tableAlias) {
                        return this.tableAlias + "." + column;
                    }
                    return column;
                })
                .collect(Collectors.joining(", "));
    }
}
