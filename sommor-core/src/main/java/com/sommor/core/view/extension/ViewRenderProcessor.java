package com.sommor.core.view.extension;

import com.sommor.extensibility.config.Extension;
import com.sommor.core.view.context.ViewRenderContext;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
@Extension(name = "视图组件渲染处理器")
public interface ViewRenderProcessor<T> {

    void processOnViewRender(T t, ViewRenderContext ctx);

}
