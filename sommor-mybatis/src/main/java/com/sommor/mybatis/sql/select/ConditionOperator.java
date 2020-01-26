package com.sommor.mybatis.sql.select;

import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public enum ConditionOperator {
    EQ("="),
    IN("in"),
    LIKE("like");

    @Getter
    private String operator;

    ConditionOperator(String operator) {
        this.operator = operator;
    }
}
