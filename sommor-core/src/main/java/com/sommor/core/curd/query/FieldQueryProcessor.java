package com.sommor.core.curd.query;

import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/5
 */
@Extension(name = "字段查询DB处理器")
public interface FieldQueryProcessor<FA> {

    void processOnQuery(FA fa, FieldQueryContext ctx);

}
