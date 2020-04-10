package com.sommor.core.component.form;

import com.sommor.extensibility.config.Implement;
import com.sommor.core.model.Model;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.extension.ViewRenderProcessor;


/**
 * @author yanguanwei@qq.com
 * @since 2020/2/16
 */
@Implement
public class FormViewRenderProcessor implements ViewRenderProcessor<FormViewConfig> {

    @Override
    public void processOnViewRender(FormViewConfig formViewConfig, ViewRenderContext ctx) {
        Model formModel = formViewConfig.getModel();
        formModel.fill(ctx.getSourceMode());

        FormView formView = ctx.getView();
        if (null != formViewConfig.getFormAction()) {
            formView.setAction(formViewConfig.getFormAction().action());
            formView.setActionTitle(formViewConfig.getFormAction().actionTitle());
        }

        formView.setData(formViewConfig.getModel().toMap());
    }
}
