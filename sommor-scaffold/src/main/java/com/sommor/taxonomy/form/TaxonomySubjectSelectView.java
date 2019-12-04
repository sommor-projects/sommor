package com.sommor.taxonomy.form;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.extensibility.reducer.Reducers;
import com.sommor.view.SelectView;
import com.sommor.view.form.EntityForm;
import com.sommor.view.form.FormFieldDefinition;

import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/1
 */
public class TaxonomySubjectSelectView extends SelectView {

    private static ExtensionExecutor<TaxonomySubjectProcessor> executor = ExtensionExecutor.of(TaxonomySubjectProcessor.class);

    @Override
    protected void doRenderForm(EntityForm form, FormFieldDefinition formFieldDefinition) {
        List<TaxonomySubjectDefinition> definitions = executor.execute(ext -> ext.getSubjectDefinition(), Reducers.collectNotNull());
        for (TaxonomySubjectDefinition definition : definitions) {
            this.addOption(definition.getTitle(), definition.getTableName());
        }
    }
}
