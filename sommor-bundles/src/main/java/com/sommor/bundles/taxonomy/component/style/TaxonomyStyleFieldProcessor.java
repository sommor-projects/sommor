package com.sommor.bundles.taxonomy.component.style;

import com.sommor.core.component.form.field.SelectView;
import com.sommor.extensibility.config.Implement;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.extension.ViewRenderProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/22
 */
@Implement
public class TaxonomyStyleFieldProcessor implements ViewRenderProcessor<TaxonomyStyleFieldConfig> {

    @Override
    public void processOnViewRender(TaxonomyStyleFieldConfig taxonomyStyleFieldConfig, ViewRenderContext ctx) {
        SelectView selectView = ctx.getView();
        selectView.addOption("select", "select");
        selectView.addOption("cascader", "cascader");
    }
}
