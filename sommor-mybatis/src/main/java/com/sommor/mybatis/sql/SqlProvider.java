package com.sommor.mybatis.sql;

import com.sommor.mybatis.entity.definition.EntityClassParser;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityDefinitionFactory;
import com.sommor.mybatis.entity.definition.FieldDefinition;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.sql.field.type.Location;
import com.sommor.mybatis.sql.select.Condition;
import com.sommor.mybatis.sql.select.Limitation;
import com.sommor.mybatis.sql.select.OrderBy;
import com.sommor.mybatis.sql.select.Pagination;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.core.DefaultParameterNameDiscoverer;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

        Map<String, Object> map;
        if (params instanceof Map) {
            map = (Map<String, Object>) params;
        } else if (parameterNames.length == 1) {
            map = new HashMap<>();
            map.put(parameterNames[0], params);
        } else {
            throw new RuntimeException("unknown params: " + params);
        }

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
            query.enrichConditionParameters();
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
        return EntityDefinitionFactory.getDefinition(entityClass);
    }

    private String doFind(EntityDefinition ed, Query query, boolean count) {
        SQL sql = new SQL()
            .SELECT(count ? "COUNT(1)" : query.projection().toString())
            .FROM(ed.getTableName() + " " + ed.getTableName());

        query.from(ed.getTableName());

        Condition condition = query.condition();
        if (null != condition) {
            sql.WHERE(condition.toExpression());
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

        for (FieldDefinition fieldDefinition : definition.getFieldDefinitions()) {
            Object v = getEntityFieldValue(fieldDefinition, entity);
            if (null != v) {
                sql.VALUES(fieldDefinition.getColumnName(), getEntityColumnValueRef(fieldDefinition));
            }
        }

        return sql.toString();
    }

    public String update(ProviderContext ctx, Object entity) {
        EntityDefinition definition = parseEntityDefinition(ctx.getMapperType());

        List<FieldDefinition> setFields = definition.getFieldDefinitions().stream()
                .filter(p-> ! p.getColumnName().equals(definition.getPrimaryKey()))
                .filter(p-> null != getEntityFieldValue(p, entity))
                .collect(Collectors.toList());

        List<FieldDefinition> conditionFields = new ArrayList<>();
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

        List<FieldDefinition> setFields = new ArrayList<>();
        for (int i=1; i<parameterNames.length; i++) {
            setFields.add(definition.getFieldDefinition(parameterNames[i]));
        }

        List<FieldDefinition> conditionFields = new ArrayList<>();
        conditionFields.add(definition.getFieldDefinition(parameterNames[0]));

        return doUpdate(definition, setFields, conditionFields);
    }

    private String doUpdate(EntityDefinition definition, List<FieldDefinition> setFields, List<FieldDefinition> conditionFields) {
        SQL sql = new SQL().UPDATE(definition.getTableName());

        for (FieldDefinition fieldDefinition : setFields) {
            sql.SET(fieldDefinition.getColumnName() + " = " + getEntityColumnValueRef(fieldDefinition));
        }

        for (FieldDefinition fieldDefinition : conditionFields) {
            sql.WHERE(fieldDefinition.getColumnName() + " = " + getEntityColumnValueRef(fieldDefinition));
        }

        return sql.toString();
    }

    public String save(ProviderContext ctx, Object entity) {
        EntityDefinition ed = parseEntityDefinition(ctx.getMapperType());

        FieldDefinition primaryField = ed.getPrimaryField();
        Object primaryValue = getEntityFieldValue(primaryField, entity);
        if (null == primaryValue) {
            return insert(ctx, entity);
        } else {
            return update(ctx, entity);
        }
    }

    public static Object getEntityFieldValue(FieldDefinition fieldDefinition, Object entity) {
        if (null == entity || ! fieldDefinition.getFieldGetMethod().getDeclaringClass().isAssignableFrom(entity.getClass())) {
            return null;
        }

        try {
            return fieldDefinition.getFieldGetMethod().invoke(entity);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private String getEntityColumnValueRef(FieldDefinition fd) {
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

        Map<String, Object> map;
        if (params instanceof Map) {
            map = (Map<String, Object>) params;
        } else {
            throw new RuntimeException("unknown params: " + params);
        }

        EntityDefinition definition = parseEntityDefinition(ctx.getMapperType());

        List<FieldDefinition> conditionFields = new ArrayList<>();
        for (int i=0; i<parameterNames.length; i++) {
            conditionFields.add(definition.getFieldDefinition(parameterNames[i]));
        }

        return doDelete(definition, conditionFields);
    }

    private String doDelete(EntityDefinition definition, List<FieldDefinition> conditionFields) {
        SQL sql = new SQL().DELETE_FROM(definition.getTableName());

        for (FieldDefinition fieldDefinition : conditionFields) {
            sql.WHERE(fieldDefinition.getColumnName() + " = " + getEntityColumnValueRef(fieldDefinition));
        }

        return sql.toString();
    }

    public String deleteById(ProviderContext ctx, Object id) {
        EntityDefinition definition = parseEntityDefinition(ctx.getMapperType());

        FieldDefinition primaryKeyField = definition.getPrimaryField();
        SQL sql = new SQL().DELETE_FROM(definition.getTableName())
            .WHERE(primaryKeyField.getColumnName() + "=" + getEntityColumnValueRef(primaryKeyField));

        return sql.toString();
    }

    private static final Map<Class, Class> ENTITY_CLASS_MAP_BY_REPO_CLASS = new ConcurrentHashMap<>(128);

    private static Class getEntityClassByRepoClass(Class repoClass) {
        Class entityClass = EntityClassParser.parse(repoClass);
        if (null == entityClass) {
            throw new RuntimeException();
        }

        return entityClass;
    }
}
