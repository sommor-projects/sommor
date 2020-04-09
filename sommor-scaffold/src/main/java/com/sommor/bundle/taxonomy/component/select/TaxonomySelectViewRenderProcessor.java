package com.sommor.bundle.taxonomy.component.select;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.model.TaxonomyKey;
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
        String parent = vc.getParent();
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

        if (isTree) {
            TaxonomyEntity typeEntity = null;
            if (StringUtils.isNotBlank(type)) {
                typeEntity = taxonomyRepository.findByType(type);
                if (null == typeEntity) {
                    throw new ErrorCodeException(ErrorCode.of("type is not exists", type));
                }
            }

            // 如果要展示
            if (null == typeEntity && StringUtils.isNotBlank(parent)) {
                TaxonomyEntity entity = taxonomyRepository.findByKey(parent);
                if (null == entity) {
                    throw new ErrorCodeException(ErrorCode.of("taxonomy is not exists", parent));
                }

                if (entity.isRoot()) {
                    typeEntity = entity;
                } else {
                    type = entity.getType();
                    typeEntity = taxonomyRepository.findByType(type);
                }
            }

            if (null != typeEntity) {
                List<TaxonomyTree> taxonomyTrees = taxonomyService.getTaxonomyTreesByType(typeEntity, group, isIncludeSelf);
                for (TaxonomyTree taxonomyTree : taxonomyTrees) {
                    selectView.addOption(taxonomyTree.toTreeOption());
                }
            }
        } else if (StringUtils.isNotBlank(type)) {
            if (StringUtils.isBlank(parent)) {
                parent = TaxonomyEntity.ROOT;
            }
            List<TaxonomyEntity> taxonomyEntities = taxonomyRepository.findByParent(parent, type);
            for (TaxonomyEntity taxonomyEntity : taxonomyEntities) {
                selectView.addOption(taxonomyEntity.getTitle(), taxonomyEntity.getName());
            }
        }
    }

}
