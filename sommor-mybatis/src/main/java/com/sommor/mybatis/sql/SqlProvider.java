package com.sommor.mybatis.sql;

import com.sommor.mybatis.sql.select.*;
import com.sommor.mybatis.entity.definition.EntityClassParser;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityManager;
import com.sommor.mybatis.entity.definition.EntityFieldDefinition;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.sql.field.type.Location;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.core.DefaultParameterNameDiscoverer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class SqlProvider {

    private static final DefaultParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    public String findById(ProviderContext ctx, Object id) {
        EntityDefinition ed = parseEntityDefinition(ctx.getMapperType());
        Query query = new Query().where(ed.getPrimaryKey(), id);

        return doFind(
            ed,
            query,
            false
        );
    }

    public String findBy(ProviderContext ctx, Object params) {
        return doFindBy(ctx, params, false);
    }

    public String countBy(ProviderContext ctx, Object params) {
        return doFindBy(ctx, params, true);
    }

    private String doFindBy(ProviderContext ctx, Object params, boolean count) {
        String[] parameterNames = NAME_DISCOVERER.getParameterNames(ctx.getMapperMethod());
        if (null == parameterNames) {
            throw new RuntimeException("parameters of method["+ctx.getMapperMethod().getName()+"] is not namePresent");
        }

        Map<String, Object> map = parseParams(params, parameterNames);

        EntityDefinition ed = parseEntityDefinition(ctx.getMapperType());
        Query query = new Query();

        for (String parameterName : parameterNames) {
            Object value = map.get(parameterName);
            query.where(parameterName, value);
        }

        return doFind(
                ed,
                query,
                count
        );
    }

    private Map<String, Object> parseParams(Object params, String[] parameterNames) {
        Map<String, Object> map;
        if (params instanceof Map) {
            map = (Map<String, Object>) params;
        } else if (parameterNames.length == 1) {
            map = new HashMap<>();
            map.put(parameterNames[0], params);
        } else {
            throw new RuntimeException("unknown params: " + params);
        }

        return map;
    }


    public String find(ProviderContext ctx, Query query) {
        enrichConditionParameters(query);

        return doFind(
            parseEntityDefinition(ctx.getMapperType()),
            query,
            false
        );
    }

    private void enrichConditionParameters(Query query) {
        if (query.getClass() == Query.class) {
            Where where = query.getWhere();
            if (null != where) {
               where.setFieldPrefix("parameters");
               for (Condition condition : where.getConditions()) {
                   for (ConditionExpression expression : condition.getExpressions()) {
                       query.addParameter(expression.getFieldName(), expression.getValue());
                   }
               }
            }
        }
    }

    public String findFirst(ProviderContext ctx, Query query) {
        enrichConditionParameters(query);

        query.limit(1);

        return doFind(
                parseEntityDefinition(ctx.getMapperType()),
                query,
                false
        );
    }

    public String findAll(ProviderContext ctx) {
        Query query = new Query();

        return doFind(
                parseEntityDefinition(ctx.getMapperType()),
                query,
                false
        );
    }

    public String count(ProviderContext ctx, Query query) {
        enrichConditionParameters(query);

        return doFind(
            parseEntityDefinition(ctx.getMapperType()),
            query,
            true
        );
    }

    public static EntityDefinition parseEntityDefinition(Class repositoryClass) {
        Class entityClass = getEntityClassByRepoClass(repositoryClass);
        return EntityManager.getDefinition(entityClass);
    }

    private String doFind(EntityDefinition ed, Query query, boolean count) {
        String tableAlias = ed.getTableAlias();

        SQL sql = new SQL()
            .SELECT(count ? "COUNT(1)" : query.projection().toString())
            .FROM("`" + ed.getTableName() + "` `" + tableAlias + "`");

        query.from(tableAlias);

        Join join = query.getJoin();
        if (null != join) {
            for (JoinClause joinClause : join.getClauses()) {
                if (joinClause.getType().equalsIgnoreCase("LEFT")) {
                    sql.LEFT_OUTER_JOIN(joinClause.toJointExpression());
                }
            }
        }

        Where where = query.getWhere();
        if (null != where) {
            String whereClause = where.toExpression();
            if (StringUtils.isNotBlank(whereClause)) {
                sql.WHERE(whereClause);
            }
        }

        if (! count) {
            OrderBy orderBy = query.orderBy();
            if (null != orderBy) {
                sql.ORDER_BY(orderBy.toExpression());
            }
        }

        String sqlString = sql.toString();

        if (! count) {
            Limitation limitation = query.limitation();
            if (null != limitation) {
                sqlString += " LIMIT " + limitation.getOffset() + ", " + limitation.getCount();
            }
        }

        return sqlString;
    }

    public String insert(ProviderContext ctx, Object entity) {
        EntityDefinition definition = parseEntityDefinition(ctx.getMapperType());

        SQL sql = new SQL().INSERT_INTO(definition.getTableName());

        for (EntityFieldDefinition fieldDefinition : definition.getFieldDefinitions()) {
            Object v = getEntityFieldValue(fieldDefinition, entity);
            if (null != v) {
                sql.VALUES(fieldDefinition.getColumnName(), getEntityColumnValueRef(fieldDefinition));
            }
        }

        return sql.toString();
    }

    public String update(ProviderContext ctx, Object entity) {
        EntityDefinition definition = parseEntityDefinition(ctx.getMapperType());

        List<EntityFieldDefinition> setFields = definition.getFieldDefinitions().stream()
                .filter(p-> ! p.getColumnName().equals(definition.getPrimaryKey()))
                .filter(p-> null != getEntityFieldValue(p, entity))
                .collect(Collectors.toList());

        List<EntityFieldDefinition> conditionFields = new ArrayList<>();
        conditionFields.add(definition.getPrimaryField());

        return doUpdate(
                definition,
                setFields,
                conditionFields
        );
    }

    public String updateBy(ProviderContext ctx, Object params) {
        String[] parameterNames = NAME_DISCOVERER.getParameterNames(ctx.getMapperMethod());
        if (null == parameterNames) {
            throw new RuntimeException("parameters of method["+ctx.getMapperMethod().getName()+"] is not namePresent");
        }

        Map<String, Object> map;
        if (params instanceof Map) {
            map = (Map<String, Object>) params;
        } else {
            throw new RuntimeException("unknown params: " + params);
        }

        EntityDefinition definition = parseEntityDefinition(ctx.getMapperType());

        List<EntityFieldDefinition> setFields = new ArrayList<>();
        for (int i=1; i<parameterNames.length; i++) {
            setFields.add(definition.getFieldDefinition(parameterNames[i]));
        }

        List<EntityFieldDefinition> conditionFields = new ArrayList<>();
        conditionFields.add(definition.getFieldDefinition(parameterNames[0]));

        return doUpdate(definition, setFields, conditionFields);
    }

    private String doUpdate(EntityDefinition definition, List<EntityFieldDefinition> setFields, List<EntityFieldDefinition> conditionFields) {
        SQL sql = new SQL().UPDATE(definition.getTableName());

        for (EntityFieldDefinition fieldDefinition : setFields) {
            sql.SET(fieldDefinition.getColumnName() + " = " + getEntityColumnValueRef(fieldDefinition));
        }

        for (EntityFieldDefinition fieldDefinition : conditionFields) {
            sql.WHERE(fieldDefinition.getColumnName() + " = " + getEntityColumnValueRef(fieldDefinition));
        }

        return sql.toString();
    }

    public String save(ProviderContext ctx, Object entity) {
        EntityDefinition ed = parseEntityDefinition(ctx.getMapperType());

        EntityFieldDefinition primaryField = ed.getPrimaryField();
        Object primaryValue = getEntityFieldValue(primaryField, entity);
        if (null == primaryValue) {
            return insert(ctx, entity);
        } else {
            return update(ctx, entity);
        }
    }

    public static Object getEntityFieldValue(EntityFieldDefinition fieldDefinition, Object entity) {
        if (null == entity || ! fieldDefinition.getGetter().getDeclaringClass().isAssignableFrom(entity.getClass())) {
            return null;
        }

        try {
            return fieldDefinition.getGetter().invoke(entity);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private String getEntityColumnValueRef(EntityFieldDefinition fd) {
        if (Location.class.equals(fd.getField().getType())) {
            return "ST_GeomFromText("+"#{" + fd.getFieldName() + "}"+")";
        }

        return "#{" + fd.getFieldName() + "}";
    }

    public String deleteBy(ProviderContext ctx, Object params) {
        String[] parameterNames = NAME_DISCOVERER.getParameterNames(ctx.getMapperMethod());
        if (null == parameterNames) {
            throw new RuntimeException("parameters of method["+ctx.getMapperMethod().getName()+"] is not namePresent");
        }

        Map<String, Object> map = parseParams(params, parameterNames);

        EntityDefinition definition = parseEntityDefinition(ctx.getMapperType());

        SQL sql = new SQL().DELETE_FROM(definition.getTableName());

        Query query = new Query();
        for (String parameterName : parameterNames) {
            Object value = map.get(parameterName);
            query.where(parameterName, value);
        }

        Where where = query.getWhere();
        if (null != where) {
            String whereClause = where.toExpression();
            if (StringUtils.isNotBlank(whereClause)) {
                sql.WHERE(whereClause);
            }
        }

        return sql.toString();
    }

    public String deleteById(ProviderContext ctx, Object id) {
        EntityDefinition definition = parseEntityDefinition(ctx.getMapperType());

        EntityFieldDefinition primaryKeyField = definition.getPrimaryField();
        SQL sql = new SQL().DELETE_FROM(definition.getTableName())
            .WHERE(primaryKeyField.getColumnName() + "=" + getEntityColumnValueRef(primaryKeyField));

        return sql.toString();
    }

    private static Class getEntityClassByRepoClass(Class repoClass) {
        Class entityClass = EntityClassParser.parse(repoClass);
        if (null == entityClass) {
            throw new RuntimeException();
        }

        return entityClass;
    }
}
