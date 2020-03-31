package com.sommor.component.orderby;

import com.sommor.component.pagination.PaginationParamField;
import com.sommor.curd.query.FieldQueryContext;
import com.sommor.curd.query.FieldQueryProcessor;
import com.sommor.extensibility.config.Implement;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Implement
public class OrderByParamFieldProcessor implements FieldQueryProcessor<PaginationParamField> {

    @Override
    public void processOnQuery(PaginationParamField paginationParamField, FieldQueryContext ctx) {
        OrderByParam orderByParam = ctx.getFieldValue();
        if (null != orderByParam) {
            ctx.getQuery().orderly(orderByParam.getField(), orderByParam.getType());
        }
    }
}
