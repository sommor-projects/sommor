package com.sommor.core.component.conditional;

import com.sommor.mybatis.sql.select.ConditionOperator;

import java.lang.annotation.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {
    ConditionOperator operator() default ConditionOperator.EQ;
}
