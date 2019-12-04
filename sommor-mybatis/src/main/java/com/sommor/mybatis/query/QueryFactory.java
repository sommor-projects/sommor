package com.sommor.mybatis.query;

import com.sommor.mybatis.query.config.QueryConfig;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class QueryFactory {
    private static final QueryOptions DEFAULT_QUERY_OPTIONS = new QueryOptions();

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

    public static Query fromParam(EntityQueryParam param) {
        Class clazz = param.getClass();

        QueryOptions queryOptions = getQueryOptions(clazz);

        Query query = new Query();
        List<ConditionalFieldDefinition> conditionalFields = ConditionParser.parse(clazz);
        if (! CollectionUtils.isEmpty(conditionalFields)) {
            for (ConditionalFieldDefinition field : conditionalFields) {
                Object value = field.getValue(param);
                if (null != value) {
                    query.where(field.fieldName, value);
                }
            }
        }

        if (queryOptions.isOrderlyEnabled()) {
            if (null != param.getOrderBy()) {
                query.orderly(param.getOrderBy(), param.getOrderType());
            }
        }

        if (queryOptions.isPageableEnabled()) {
            query.pageable(param.getPageNo(), param.getPageSize());
        }

        return query;
    }
}
