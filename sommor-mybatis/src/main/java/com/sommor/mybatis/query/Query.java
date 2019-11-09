package com.sommor.mybatis.query;

import com.sommor.mybatis.entity.naming.NamingParseStrategy;
import com.sommor.mybatis.query.config.Conditional;
import com.sommor.mybatis.query.config.QueryConfig;
import com.sommor.mybatis.sql.select.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/24
 */
public class Query<Q extends Query> {
    private static final QueryOptions DEFAULT_QUERY_OPTIONS = new QueryOptions();

    @Getter @Setter
    private Integer page;

    @Getter @Setter
    private Integer pageSize;

    @Getter @Setter
    private String orderBy;

    @Getter @Setter
    private String orderType;

    private Boolean pageableEnabled;

    private Boolean orderlyEnabled;

    private Projection projection;

    private Condition condition;

    private static final Map<Class, QueryOptions> QUERY_OPTIONS_MAP = new HashMap<>(128);

    private static QueryOptions getQueryOptions(Class clazz) {
        QueryOptions queryOptions = QUERY_OPTIONS_MAP.get(clazz);
        if (null == queryOptions) {
            synchronized (QUERY_OPTIONS_MAP) {
                queryOptions = QUERY_OPTIONS_MAP.get(clazz);
                if (null == queryOptions) {
                    QueryConfig queryConfig = (QueryConfig) clazz.getAnnotation(QueryConfig.class);
                    if (null != queryConfig) {
                        queryOptions = new QueryOptions();
                        queryOptions.setPageableEnabled(queryConfig.enablePageable());
                        queryOptions.setOrderlyEnabled(queryConfig.enableOrderly());
                    } else {
                        queryOptions = DEFAULT_QUERY_OPTIONS;
                    }

                    QUERY_OPTIONS_MAP.put(clazz, queryOptions);
                }
            }
        }

        return queryOptions;
    }

    public Q select(String expressions) {
        Projection projection = new Projection();
        projection.columns(expressions);

        this.projection = projection;

        return (Q) this;
    }

    public Projection projection() {
        return null == this.projection ? new Projection().all() : this.projection;
    }

    public Pagination pagination() {
        if (! isPageableEnabled()) {
            return null;
        }

        return Pagination.of(this.getPage(), this.getPageSize());
    }

    public Q enablePageable() {
        this.pageableEnabled = true;

        return (Q) this;
    }

    public Q pageable(Integer page, Integer pageSize) {
        this.setPage(page);
        this.setPageSize(pageSize);

        this.enablePageable();

        return (Q) this;
    }

    public Q enableOrderly() {
        this.orderlyEnabled = true;

        return (Q) this;
    }

    public Q orderly(String orderBy, OrderType orderType) {
        this.setOrderBy(orderBy);
        this.setOrderType(orderType.name());

        this.enableOrderly();

        return (Q) this;
    }

    private boolean isPageableEnabled() {
        return Boolean.TRUE.equals(this.pageableEnabled)
            || Boolean.TRUE.equals(getQueryOptions(this.getClass()).getPageableEnabled());
    }

    public OrderBy orderBy() {
        if (! isOrderlyEnabled()) {
            return null;
        }

        String orderBy = this.getOrderBy();

        if (null != orderBy) {
            OrderType orderType = OrderType.of(this.getOrderType());
            String orderByColumnName = NamingParseStrategy.INST.parseFieldName2ColumnName(orderBy);
            return new OrderBy(orderByColumnName, orderType == null ? OrderType.DESC : orderType);
        }

        return null;
    }

    private boolean isOrderlyEnabled() {
        return Boolean.TRUE.equals(this.orderlyEnabled)
        || Boolean.TRUE.equals(getQueryOptions(this.getClass()).getOrderlyEnabled());
    }

    public Condition condition() {
        Condition condition = new Condition(this.condition);

        List<ConditionalField> conditionalFields = parseConditionalFields(this.getClass());
        if (! CollectionUtils.isEmpty(conditionalFields)) {
            for (ConditionalField field : conditionalFields) {
                Object value = field.getValue(this);
                if (null != value) {
                    condition.and(field.columnName, field.fieldName, field.operator, value);
                }
            }
        }

        return condition;
    }

    public Q where(String fieldName, Object value) {
        this.addConditional(null, fieldName, "=", value);
        return (Q) this;
    }

    private void addConditional(String columnName, String fieldName, String operator, Object value) {
        if (null == this.condition) {
            this.condition = new Condition();
        }

        if (null == columnName) {
            columnName = NamingParseStrategy.INST.parseFieldName2ColumnName(fieldName);
        }

        this.condition.and(columnName, fieldName, operator, value);
    }

    private static class ConditionalField {
        String columnName;
        String fieldName;
        Field field;
        Method method;
        String operator;

        Object getValue(Object o) {
            try {
                if (null != field) {
                    return field.get(o);
                }

                if (null != method) {
                    return method.invoke(o);
                }
            } catch (Throwable e) {
                throw new RuntimeException("get value exception, column: "+columnName+", class: " + o.getClass().getName());
            }

            return null;
        }
    }

    private final static Map<Class, List<ConditionalField>> conditionalFieldsMap = new HashMap<>(128);

    private static List<ConditionalField> parseConditionalFields(Class clazz) {
        List<ConditionalField> fields = conditionalFieldsMap.get(clazz);
        if (null == fields) {
            synchronized (conditionalFieldsMap) {
                fields = conditionalFieldsMap.get(clazz);
                if (null == fields) {
                    fields = new ArrayList<>();

                    for (Field field : clazz.getDeclaredFields()) {
                        Conditional conditional = field.getAnnotation(Conditional.class);
                        if (null != conditional) {
                            String fieldName = field.getName();
                            field.setAccessible(true);
                            ConditionalField conditionalField = new ConditionalField();
                            conditionalField.columnName = NamingParseStrategy.INST.parseFieldName2ColumnName(fieldName);
                            conditionalField.fieldName = fieldName;
                            conditionalField.field = field;
                            conditionalField.operator = conditional.operator();

                            fields.add(conditionalField);
                        }
                    }

                    for (Method method : clazz.getMethods()) {
                        if (method.getModifiers() == Modifier.PUBLIC
                            && method.getParameterCount() == 0
                            && method.getName().startsWith("get")) {


                            Conditional conditional = method.getAnnotation(Conditional.class);
                            if (null != conditional) {
                                String fieldName = method.getName().substring(3);
                                method.setAccessible(true);

                                ConditionalField conditionalField = new ConditionalField();
                                conditionalField.columnName = NamingParseStrategy.INST.parseFieldName2ColumnName(fieldName);
                                conditionalField.fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                                conditionalField.method = method;
                                conditionalField.operator = conditional.operator();

                                fields.add(conditionalField);
                            }
                        }
                    }
                }

                conditionalFieldsMap.put(clazz, fields);
            }
        }

        return fields;
    }
}
