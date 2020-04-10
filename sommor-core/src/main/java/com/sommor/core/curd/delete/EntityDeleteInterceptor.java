package com.sommor.core.curd.delete;

import com.sommor.extensibility.config.Extension;
import com.sommor.mybatis.entity.BaseEntity;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/5
 */
@Extension(name = "实体删除拦截器")
public interface EntityDeleteInterceptor {

    void interceptOnEntityDelete(BaseEntity entity);

}
