package com.sommor.mybatis.sql;

import com.sommor.mybatis.entity.definition.EntityClassParser;
import com.sommor.mybatis.entity.definition.EntityDefinition;
import com.sommor.mybatis.entity.definition.EntityDefinitionFactory;
import com.sommor.mybatis.entity.definition.FieldDefinition;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.sql.field.type.Location;
import com.sommor.mybatis.sql.select.Condition;
import com.sommor.mybatis.sql.select.OrderBy;
import com.sommor.mybatis.sql.select.Pagination;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.core.DefaultParameterNameDiscoverer;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
            false
        );
    }

    public String find(ProviderContext ctx, Query query) {
        return doFind(
            parseEntityDefinition(ctx.getMapperType()),
            query,
            false
        );
    }


    public String count(ProviderContext ctx, Query query) {
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
            .FROM(ed.getTableName());

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
            Pagination pagination = query.pagination();
            if (null != pagination) {
                sqlString += " LIMIT " + pagination.getOffset() + ", " + pagination.getPageSize();
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

        SQL sql = new SQL().UPDATE(definition.getTableName());
        String primaryKey = definition.getPrimaryKey();
        for (FieldDefinition fieldDefinition : definition.getFieldDefinitions()) {
            if (! primaryKey.equals(fieldDefinition.getColumnName())) {
                Object v = getEntityFieldValue(fieldDefinition, entity);
                if (null != v) {
                    sql.SET(fieldDefinition.getColumnName() + " = " + getEntityColumnValueRef(fieldDefinition));
                }
            }
        }

        sql.WHERE(definition.getPrimaryKey() + " = " + getEntityColumnValueRef(definition.getPrimaryField()));

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
