package com.sommor.scaffold.fields.orderby;

import com.sommor.extensibility.config.Implement;
import com.sommor.scaffold.view.field.FieldQueryContext;
import com.sommor.scaffold.fields.pagination.PaginationParamField;
import com.sommor.scaffold.view.field.FieldProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Implement
public class OrderByParamFieldProcessor implements FieldProcessor<PaginationParamField> {

    @Override
    public void processOnQuery(PaginationParamField paginationParamField, FieldQueryContext ctx) {
        OrderByParam orderByParam = ctx.getFieldValue();
        if (null != orderByParam) {
            ctx.getQuery().orderly(orderByParam.getField(), orderByParam.getType());
        }
    }
}
