package com.sommor.mybatis.sql.select;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/27
 */
public class Where {

    @Getter
    private List<Condition> conditions = new ArrayList<>();

    public void setColumnPrefix(String columnPrefix) {
        for (Condition condition : conditions) {
            condition.setColumnPrefix(columnPrefix);
        }
    }

    public void setFieldPrefix(String fieldPrefix) {
        for (Condition condition : conditions) {
            condition.setFieldPrefix(fieldPrefix);
        }
    }

    public Condition condition() {
        Condition condition = new Condition();
        this.conditions.add(condition);

        return condition;
    }

    public String toExpression() {
        if (CollectionUtils.isEmpty(this.conditions)) {
            return null;
        }

        return this.conditions.stream()
                .map(p->p.toExpression())
                .filter(s -> StringUtils.isNotBlank(s))
                .collect(Collectors.joining(" AND "));
    }

}
