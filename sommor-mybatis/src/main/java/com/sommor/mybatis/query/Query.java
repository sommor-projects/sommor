package com.sommor.mybatis.query;

import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityManager;
import com.sommor.mybatis.entity.naming.NamingParseStrategy;
import com.sommor.mybatis.sql.select.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class Query {
    private Projection projection;

    @Getter
    private Join join;

    @Getter
    private Where where;

    private OrderBy orderBy;

    @Getter @Setter
    private Limitation limitation;

    @Getter
    private Map<String, Object> parameters = new HashMap<>();

    public Query from(String table) {
        if (null != this.projection) {
            this.projection.setTableAlias(table);
        }

        if (null != this.where) {
            this.where.setColumnPrefix(table);
        }

        if (null != this.orderBy) {
            this.orderBy.setTableAlias(table);
        }

        return this;
    }

    public Query select(String expressions) {
        Projection projection = new Projection();
        projection.columns(expressions);

        this.projection = projection;

        return this;
    }

    public Join join() {
        return this.join == null ? (this.join = new Join()) : this.join;
    }

    public Query leftJoin(Class entityClass, String condition, String... fields) {
        EntityDefinition ed = EntityManager.getDefinition(entityClass);
        return leftJoin(ed.getSubjectName(), condition, fields);
    }

    public Query leftJoin(String subject, String condition, String... fields) {
        this.join().left(subject, condition);

        Projection projection = this.projection();
        for (String field : fields) {
            projection.columns(subject + "." + field);
        }
        return this;
    }

    public Query rightJoin(String subject, String condition) {
        this.join().right(subject, condition);
        return this;
    }

    public Projection projection() {
        return null == this.projection ? (this.projection = new Projection().all()) : this.projection;
    }

    public Pagination pagination() {
        if (this.limitation instanceof Pagination) {
            return (Pagination) this.limitation;
        }

        return null;
    }

    public Limitation limitation() {
        return this.limitation;
    }

    public Query pageable(Integer page, Integer pageSize) {
        this.setLimitation(Pagination.of(page, pageSize));
        return this;
    }

    public Query limit(Integer count) {
        this.setLimitation(new Limitation(0, count));
        return this;
    }

    public Query orderly(String orderBy, String orderType) {
        return this.orderly(orderBy, orderType == null ? OrderType.ASC : OrderType.valueOf(orderType));
    }

    public Query orderly(String orderBy, OrderType orderType) {
        String orderByColumnName = NamingParseStrategy.INST.parseFieldName2ColumnName(orderBy);
        this.orderBy = new OrderBy(orderByColumnName, orderType == null ? OrderType.DESC : orderType);
        return this;
    }

    public OrderBy orderBy() {
        return this.orderBy;
    }

    public Where where() {
        if (null == this.where) {
            this.where = new Where();
        }
        return this.where;
    }

    public Query where(String fieldName, Object value) {
        this.where().condition().and(fieldName, value);
        return this;
    }

    public boolean hasWhereClause() {
        return null != this.where
                && CollectionUtils.isNotEmpty(this.where.getConditions())
                && CollectionUtils.isNotEmpty(this.where.getConditions().get(0).getExpressions());
    }

    public void addParameter(String key, Object value) {
        this.parameters.put(key, value);
    }
}
