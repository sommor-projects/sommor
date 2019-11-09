package com.sommor.taxonomy.form;

import com.sommor.extensibility.config.Implement;
import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.taxonomy.repository.TaxonomyRepository;
import com.sommor.view.FieldView;
import com.sommor.view.FormView;
import com.sommor.view.SelectView;
import com.sommor.view.form.FormField;
import com.sommor.view.form.FormFieldDefinition;
import com.sommor.view.form.FormFieldResolver;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Implement
public class TaxonomySelectResolver implements FormFieldResolver<TaxonomySelect> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Override
    public void resolveOnInit(TaxonomySelect taxonomySelect, FormFieldDefinition definition) {
    }

    @Override
    public void resolveOnRender(TaxonomySelect taxonomySelect, FieldView view, FormView formView, FormField formField) {
        SelectView selectView = (SelectView) view;

        if (taxonomySelect.multiple()) {
            selectView.multiple();
        }

        int parentId = taxonomySelect.parentId();
        if (parentId < 0) {
            Integer pid = null;

            if (taxonomySelect.parentIsSelf()) {
                pid = (Integer) formField.getValue();
            } else {
                FormField parentIdField = formView.getFormField("parentId");
                if (null != parentIdField) {
                    pid = (Integer) parentIdField.getValue();
                }
            }

            if (null != pid) {
                parentId = pid;
            }

            if (parentId < 0) {
                throw new RuntimeException("parentId should be specified");
            }
        }

        TaxonomyEntity entity = taxonomyRepository.findById(parentId);
        if (null == entity) {
            throw new RuntimeException("parentId should not exists");
        }

        List<TaxonomyEntity> list = taxonomyRepository.findTaxonomiesByRootId(entity.getRootId());
        for (TaxonomyEntity taxonomyEntity : list) {
            selectView.addOption(String.valueOf(taxonomyEntity.getId()), taxonomyEntity.getTitle());
        }
    }
}
