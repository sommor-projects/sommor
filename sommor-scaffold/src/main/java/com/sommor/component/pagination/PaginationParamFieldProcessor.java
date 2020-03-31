package com.sommor.component.pagination;

import com.sommor.curd.query.FieldQueryContext;
import com.sommor.curd.query.FieldQueryProcessor;
import com.sommor.extensibility.config.Implement;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
@Implement
public class PaginationParamFieldProcessor implements FieldQueryProcessor<PaginationParamField> {

    @Override
    public void processOnQuery(PaginationParamField paginationParamField, FieldQueryContext ctx) {
        PaginationParam paginationParam = ctx.getFieldValue();
        if (null != paginationParam) {
            ctx.getQuery().pageable(paginationParam.getPageNo(), paginationParam.getPageSize());
        }
    }
}
