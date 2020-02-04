package com.sommor.mybatis.sql.select;

import com.sommor.mybatis.entity.definition.EntityDefinition;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
public class JoinClause {

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private EntityDefinition jointEntity;

    @Getter
    @Setter
    private String condition;

    public String toExpression() {
        StringBuilder builder = new StringBuilder();

        EntityDefinition jointEntity = this.getJointEntity();

        builder.append(getType()).append(" JOIN ")
                .append(jointEntity.getTableName()).append(" ").append(jointEntity.getTableAlias())
                .append(" ON ").append(this.getCondition());

        return builder.toString();
    }

    public String toJointExpression() {
        StringBuilder builder = new StringBuilder();
        EntityDefinition jointEntity = this.getJointEntity();
        builder.append(jointEntity.getTableName()).append(" ").append(jointEntity.getTableAlias())
                .append(" ON ").append(this.getCondition());

        return builder.toString();
    }
}
