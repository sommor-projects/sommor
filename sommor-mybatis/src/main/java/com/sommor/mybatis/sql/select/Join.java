package com.sommor.mybatis.sql.select;

import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityManager;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/1
 */
public class Join {

    @Getter
    private List<JoinClause> clauses = new ArrayList<>();

    public Join join(String type, String subject, String condition) {
        JoinClause clause = new JoinClause();
        clause.setType(type);

        EntityDefinition ed = EntityManager.getDefinitionBySubject(subject);
        if (null == ed) {
            throw new RuntimeException("unknown subject: "+subject);
        }
        clause.setJointEntity(ed);

        clause.setCondition(condition);
        this.clauses.add(clause);

        return this;
    }

    public Join left(String subject, String condition) {
        return this.join("LEFT", subject, condition);
    }

    public Join right(String subject, String condition) {
        return this.join("RIGHT", subject, condition);
    }

    public String toExpression() {
        List<JoinClause> clauses = this.clauses;

        if (CollectionUtils.isEmpty(clauses)) {
            return "";
        }

        return clauses.stream().map(c -> c.toExpression())
                .collect(Collectors.joining(" "));
    }

}
