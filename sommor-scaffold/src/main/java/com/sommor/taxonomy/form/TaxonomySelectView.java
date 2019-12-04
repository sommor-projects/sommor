package com.sommor.taxonomy.form;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.taxonomy.model.TaxonomyTree;
import com.sommor.taxonomy.repository.TaxonomyRepository;
import com.sommor.taxonomy.service.TaxonomyService;
import com.sommor.view.OptionTree;
import com.sommor.view.SelectView;
import com.sommor.view.form.EntityForm;
import com.sommor.view.form.FormFieldDefinition;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;


/**
 * @author yanguanwei@qq.com
 * @since 2019/11/25
 */
public class TaxonomySelectView extends SelectView {
    @Setter
    private Integer typeId;
    @Setter
    private String type;
    @Setter
    private Integer parentId;

    @Setter
    private boolean tree;

    @Setter
    private boolean rootSelection;

    private TaxonomyRepository taxonomyRepository = ApplicationContextHolder.getBean(TaxonomyRepository.class);
    private TaxonomyService taxonomyService = ApplicationContextHolder.getBean(TaxonomyService.class);

    @Override
    protected void doRenderForm(EntityForm form, FormFieldDefinition formFieldDefinition) {
        super.doRenderForm(form, formFieldDefinition);


        Integer parentId = this.parentId;
        Integer typeId = this.typeId;
        String type = this.type;

        if (this.tree) {
            if (null == typeId || typeId <= 0) {
                if (StringUtils.isNoneBlank(type)) {
                    TaxonomyEntity typeEntity = taxonomyRepository.findByType(type);
                    if (null == typeEntity) {
                        throw new ErrorCodeException(ErrorCode.of("type is not exists", type));
                    }
                    typeId = typeEntity.getId();
                } else if (null != parentId && parentId > 0) {
                    TaxonomyEntity parentEntity = taxonomyRepository.findById(parentId);
                    if (null == parentEntity) {
                        throw new ErrorCodeException(ErrorCode.of("parentId is not exists", parentId));
                    }
                    typeId = parentEntity.getTypeId() == 0 ? parentId : parentEntity.getTypeId();
                }
            }

            if (this.rootSelection) {
                this.addOptionTree(new OptionTree("0", "最顶层分类"));
            }

            if (null != typeId && typeId > 0) {
                List<TaxonomyTree> taxonomyTrees = taxonomyService.getTaxonomyTreesByType(typeId, this.rootSelection);
                for (TaxonomyTree taxonomyTree : taxonomyTrees) {
                    this.addOptionTree(taxonomyTree.toOptionTree());
                }
            }
        } else {
            if (null != parentId) {
                List<TaxonomyEntity> list = taxonomyRepository.findByParentId(parentId);
                for (TaxonomyEntity taxonomyEntity : list) {
                    String title = taxonomyEntity.getTitle() + "(" + taxonomyEntity.getSubTitle() + ")";
                    this.addOption(title, taxonomyEntity.getId());
                }
            }
        }
    }
}
