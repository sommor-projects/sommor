package com.sommor.taxonomy.form;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
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
    public void resolveOnRender(TaxonomySelect taxonomySelect, FieldView view, FormView formView, FormField formField) {
        SelectView selectView = (SelectView) view;

        if (taxonomySelect.multiple()) {
            selectView.multiple();
        }

        int parentId = taxonomySelect.parentId();
        int rootId = taxonomySelect.rootId();
        if (parentId > 0) {
            TaxonomyEntity entity = taxonomyRepository.findById(parentId);
            if (null == entity) {
                throw new ErrorCodeException(ErrorCode.of("parentId is not exists", parentId));
            }
            rootId = entity.getRootId();
        } else if (rootId <= 0) {
            FormField rootIdField = formView.getFormField("rootId");
            if (null != rootIdField) {
                Integer rootIdInteger = (Integer) rootIdField.getValue();
                if (null != rootIdInteger && rootIdInteger > 0) {
                    rootId = rootIdInteger;
                    TaxonomyEntity entity = taxonomyRepository.findById(rootId);
                    if (null == entity) {
                        throw new ErrorCodeException(ErrorCode.of("rootId is not exists", rootId));
                    }
                }
            }
        }

        if (rootId > 0) {
            List<TaxonomyEntity> list = taxonomyRepository.findTaxonomiesByRootId(rootId);
            for (TaxonomyEntity taxonomyEntity : list) {
                selectView.addOption(taxonomyEntity.getId(), taxonomyEntity.getParentId(), taxonomyEntity.getTitle());
            }
        }


    }
}
