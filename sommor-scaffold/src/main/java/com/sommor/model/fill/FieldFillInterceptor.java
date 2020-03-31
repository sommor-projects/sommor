package com.sommor.model.fill;

import com.sommor.extensibility.config.Extension;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
@Extension(name = "字段填充拦截器")
public interface FieldFillInterceptor {

    Object interceptOnFieldFill(FieldFillContext ctx);

}
