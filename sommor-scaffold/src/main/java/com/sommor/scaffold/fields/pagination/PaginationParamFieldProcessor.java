package com.sommor.scaffold.fields.pagination;

import com.sommor.extensibility.config.Implement;
import com.sommor.scaffold.view.field.FieldProcessor;
import com.sommor.scaffold.view.field.FieldQueryContext;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Implement
public class PaginationParamFieldProcessor implements FieldProcessor<PaginationParamField> {

    @Override
    public void processOnQuery(PaginationParamField paginationParamField, FieldQueryContext ctx) {
        PaginationParam paginationParam = ctx.getFieldValue();
        if (null != paginationParam) {
            ctx.getQuery().pageable(paginationParam.getPageNo(), paginationParam.getPageSize());
        }
    }
}
