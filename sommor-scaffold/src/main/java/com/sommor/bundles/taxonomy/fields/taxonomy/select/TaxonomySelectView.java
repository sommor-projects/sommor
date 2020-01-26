package com.sommor.bundles.taxonomy.fields.taxonomy.select;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.scaffold.spring.ApplicationContextHolder;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.model.TaxonomyTree;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.bundles.taxonomy.service.TaxonomyService;
import com.sommor.scaffold.view.TreeOption;
import com.sommor.scaffold.view.SelectView;
import lombok.Getter;
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

    @Setter
    private boolean selfSelection;

    private TaxonomyRepository taxonomyRepository = ApplicationContextHolder.getBean(TaxonomyRepository.class);
    private TaxonomyService taxonomyService = ApplicationContextHolder.getBean(TaxonomyService.class);

    @Override
    protected void onFieldRender() {
        Integer parentId = this.parentId;
        Integer typeId = this.typeId;
        String type = this.type;

        if (this.tree) {
        }

        if (this.tree) {
            this.tree();

            if (null == typeId || typeId <= 0) {
                if (StringUtils.isNoneBlank(type)) {
                    TaxonomyEntity typeEntity = taxonomyRepository.findByType(type);
                    if (null == typeEntity) {
                        throw new ErrorCodeException(ErrorCode.of("type is not exists", type));
                    }
                    typeId = typeEntity.getId();
                } else if (null != parentId && parentId >= 0) {
                    TaxonomyEntity parentEntity = taxonomyRepository.findById(parentId);
                    if (null == parentEntity) {
                        throw new ErrorCodeException(ErrorCode.of("parentId is not exists", parentId));
                    }
                    typeId = parentEntity.getTypeId() == 0 ? parentId : parentEntity.getTypeId();
                }
            }

            if (this.rootSelection) {
                this.addOption(new TreeOption("最顶层分类", 0));
            }

            if (null != typeId && typeId > 0) {
                List<TaxonomyTree> taxonomyTrees = taxonomyService.getTaxonomyTreesByType(typeId, this.selfSelection);
                for (TaxonomyTree taxonomyTree : taxonomyTrees) {
                    this.addOption(taxonomyTree.toTreeOption());
                }
            }
        } else if (null != parentId && parentId >= 0) {
            List<TaxonomyEntity> taxonomyEntities = taxonomyRepository.findByParentId(parentId);
            for (TaxonomyEntity taxonomyEntity : taxonomyEntities) {
                this.addOption(taxonomyEntity.getTitle(), taxonomyEntity.getId());
            }
        }
    }
}
