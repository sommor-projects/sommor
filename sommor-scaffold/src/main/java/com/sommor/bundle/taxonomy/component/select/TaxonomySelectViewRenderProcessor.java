package com.sommor.bundle.taxonomy.component.select;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.model.TaxonomyTree;
import com.sommor.bundle.taxonomy.repository.TaxonomyRepository;
import com.sommor.bundle.taxonomy.service.TaxonomyService;
import com.sommor.component.form.field.TreeOption;
import com.sommor.extensibility.config.Implement;
import com.sommor.component.form.field.SelectView;
import com.sommor.view.context.ViewRenderContext;
import com.sommor.view.extension.ViewRenderProcessor;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/14
 */
@Implement
public class TaxonomySelectViewRenderProcessor implements ViewRenderProcessor<TaxonomySelectFieldConfig> {

    @Resource
    private TaxonomyService taxonomyService;

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Override
    public void processOnViewRender(TaxonomySelectFieldConfig vc, ViewRenderContext ctx) {
        String type = vc.getType();
        Integer typeId = vc.getTypeId();
        Integer parentId = vc.getParentId();
        String group = vc.getGroup();

        boolean isTree = Boolean.TRUE.equals(vc.getTree());
        boolean isMultiple = Boolean.TRUE.equals(vc.getMultiple());
        boolean isIncludeSelf = Boolean.TRUE.equals(vc.getIncludeSelf());
        boolean isIncludeRoot = Boolean.TRUE.equals(vc.getIncludeRoot());

        SelectView selectView = ctx.getView();

        if (isTree) {
            selectView.tree();
        }

        if (isMultiple) {
            selectView.multiple();
        }

        if (isIncludeRoot) {
            selectView.addOption(new TreeOption("最顶层分类", 0));
        }

        if (StringUtils.isNotBlank(type)) {
            TaxonomyEntity taxonomyEntity = taxonomyRepository.findByName(type);
            if (null == taxonomyEntity) {
                throw new ErrorCodeException(ErrorCode.of("type is not exists", type));
            }
            typeId = taxonomyEntity.getId();
        }

        // 如果要展示
        if (isTree && (null == typeId || typeId < 0 ) && parentId != null && parentId > 0) {
            TaxonomyEntity taxonomyEntity = taxonomyRepository.findById(parentId);
            if (null == taxonomyEntity) {
                throw new ErrorCodeException(ErrorCode.of("taxonomy is not exists", parentId));
            }
            typeId = taxonomyEntity.getTypeId() == 0 ? taxonomyEntity.getId() : taxonomyEntity.getTypeId();
        }

        if (null != typeId && typeId >= 0) {
            List<TaxonomyTree> taxonomyTrees = taxonomyService.getTaxonomyTreesByType(typeId, group, isIncludeSelf);
            for (TaxonomyTree taxonomyTree : taxonomyTrees) {
                selectView.addOption(taxonomyTree.toTreeOption());
            }
        } else if (null != parentId && parentId >= 0) {
            List<TaxonomyEntity> taxonomyEntities = taxonomyRepository.findByParentId(parentId);
            for (TaxonomyEntity taxonomyEntity : taxonomyEntities) {
                selectView.addOption(taxonomyEntity.getTitle(), taxonomyEntity.getId());
            }
        }
    }

}
