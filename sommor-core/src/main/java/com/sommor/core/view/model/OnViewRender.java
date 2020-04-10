package com.sommor.core.view.model;

import com.sommor.core.model.Model;
import com.sommor.core.view.context.ViewRenderContext;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
public interface OnViewRender {

    void onViewRender(Model model, ViewRenderContext ctx);

}
