package com.sommor.curd.query;

import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/5
 */
@Extension(name = "字段查询DB拦截器")
public interface FieldQueryInterceptor {

    void interceptOnQuery(FieldQueryContext ctx);

}
