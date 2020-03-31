package com.sommor.view.model;

import com.sommor.model.Model;
import com.sommor.view.context.ViewRenderContext;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
public interface OnViewRender {

    void onViewRender(Model model, ViewRenderContext ctx);

}
