package com.sommor.bundles.taxonomy.component.select;

import com.sommor.extensibility.config.Implement;
import com.sommor.core.view.context.ViewRenderContext;
import com.sommor.core.view.extension.ViewRenderProcessor;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Implement
public class TaxonomySelectFieldProcessor implements ViewRenderProcessor<TaxonomySelectField> {

    @Override
    public void processOnViewRender(TaxonomySelectField taxonomySelect, ViewRenderContext ctx) {
        TaxonomySelectFieldConfig vc = ctx.getViewConfig();

        if (! taxonomySelect.type().isEmpty()) {
            vc.setType(taxonomySelect.type());
        }

        if (taxonomySelect.multiple()) {
            vc.setMultiple(true);
        }

        if (! taxonomySelect.type().isEmpty()) {
            vc.setType(taxonomySelect.type());
        }

        if (! taxonomySelect.group().isEmpty()) {
            vc.setGroup(taxonomySelect.group());
        }

        if (taxonomySelect.tree()) {
            vc.setTree(true);
        }

        vc.setIncludeRoot(taxonomySelect.includeRoot());
        vc.setIncludeSelf(taxonomySelect.includeSelf());
    }
}
