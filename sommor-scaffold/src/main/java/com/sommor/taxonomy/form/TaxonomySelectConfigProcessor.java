package com.sommor.taxonomy.form;

import com.sommor.extensibility.config.Implement;
import com.sommor.view.FieldView;
import com.sommor.view.form.FieldConfigProcessor;
import com.sommor.view.form.EntityForm;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Implement
public class TaxonomySelectConfigProcessor implements FieldConfigProcessor<TaxonomySelect> {

    @Override
    public void processOnFormRender(TaxonomySelect taxonomySelect, FieldView view, EntityForm form) {
        TaxonomySelectView selectView = (TaxonomySelectView) view;

        boolean isTree = taxonomySelect.tree();

        if (taxonomySelect.multiple()) {
            selectView.multiple();
        }

        if (taxonomySelect.parentId() >= 0) {
            selectView.setParentId(taxonomySelect.parentId());
        }

        if (taxonomySelect.typeId() >= 0) {
            selectView.setTypeId(taxonomySelect.typeId());
        }

        selectView.setTree(taxonomySelect.tree());
        selectView.setRootSelection(taxonomySelect.rootSelection());
    }
}
