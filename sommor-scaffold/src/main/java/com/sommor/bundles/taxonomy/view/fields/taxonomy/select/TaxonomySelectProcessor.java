package com.sommor.bundles.taxonomy.view.fields.taxonomy.select;

import com.sommor.extensibility.config.Implement;
import com.sommor.core.view.field.FieldProcessor;
import com.sommor.core.view.field.FieldRenderContext;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Implement
public class TaxonomySelectProcessor implements FieldProcessor<TaxonomySelect> {

    @Override
    public void processOnFormRender(TaxonomySelect taxonomySelect, FieldRenderContext ctx) {
        TaxonomySelectView selectView = ctx.getFieldView();

        if (taxonomySelect.multiple()) {
            selectView.multiple();
        }

        if (taxonomySelect.parentId() >= 0) {
            selectView.setParentId(taxonomySelect.parentId());
        }

        if (taxonomySelect.typeId() >= 0) {
            selectView.setTypeId(taxonomySelect.typeId());
        }

        if (! taxonomySelect.group().isEmpty()) {
            selectView.setGroup(taxonomySelect.group());
        }

        if (taxonomySelect.tree()) {
            selectView.tree();
        }

        selectView.setRootSelection(taxonomySelect.rootSelection());
        selectView.setSelfSelection(taxonomySelect.selfSelection());
    }
}
