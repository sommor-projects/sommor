package com.sommor.taxonomy.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.extensibility.config.Implement;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.scaffold.service.EntityAnnotatedProcessor;
import com.sommor.scaffold.utils.Parameters;
import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.taxonomy.form.TaxonomyForm;
import com.sommor.taxonomy.model.*;
import com.sommor.taxonomy.param.TaxonomyFormRenderParam;
import com.sommor.taxonomy.param.TaxonomyInfoParam;
import com.sommor.taxonomy.param.TaxonomyQueryParam;
import com.sommor.taxonomy.repository.TaxonomyRepository;
import com.sommor.taxonomy.utils.SlugParser;
import com.sommor.view.BreadcrumbView;
import com.sommor.view.PageView;
import com.sommor.view.RouteLinkView;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/27
 */
@Implement
public class TaxonomyProcessor implements EntityAnnotatedProcessor<
        TaxonomyEntity,
        TaxonomyForm,
        TaxonomyFormRenderParam,
        TaxonomyDetail,
        TaxonomyInfoParam,
        TaxonomyItem,
        TaxonomyQueryParam> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    @Override
    public void processOnInitQuery(TaxonomyQueryParam param) {
        if (param.getParentId() == null) {
            if (null != param.getSlug()) {
                Integer typeId = Parameters.get(param.getTypeId(), 0);
                TaxonomyEntity entity = taxonomyRepository.findBySlug(typeId, param.getSlug());
                if (null == entity) {
                    throw new ErrorCodeException(ErrorCode.of("entity.list.render.slug.invalid", param.getSlug()));
                }
                param.setParentId(entity.getId());
            } else {
                param.setParentId(0);
            }

        }
    }

    @Override
    public TaxonomyEntity findEntityByInfoParam(TaxonomyInfoParam param) {
        TaxonomyEntity taxonomyEntity = null;
        if (null != param.getSlug()) {
            Integer typeId = param.getTypeId();
            if (null == typeId && param.getType() != null) {
                TaxonomyEntity root = taxonomyRepository.findByType(param.getType());
                typeId = root.getId();
            } else {
                typeId = 0;
            }

            taxonomyEntity = taxonomyRepository.findBySlug(typeId, param.getSlug());
        } else if (null != param.getType()) {
            taxonomyEntity = taxonomyRepository.findByType(param.getType());
        }

        return taxonomyEntity;
    }

    @Override
    public void processOnInfoRender(TaxonomyEntity entity, PageView<TaxonomyDetail> pageView, TaxonomyInfoParam param) {
        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(entity.getId());

        pageView.setTitle(entity.getTitle());
        pageView.setBreadcrumb(renderBreadcrumbView(param.getType(), param.getSubject(), paths));

        TaxonomyDetail taxonomyDetail = new TaxonomyDetail();
        taxonomyDetail.setPaths(paths.stream().map(p -> {
            TaxonomyItem item = new TaxonomyItem();
            item.from(p);
            return item;
        }).collect(Collectors.toList()));

        pageView.setContent(taxonomyDetail);
    }

    @Override
    public void processOnRenderListItem(TaxonomyEntity entity, TaxonomyItem item) {
        item.from(entity);
        item.setSubTaxonomyCount(taxonomyRepository.countByParentId(entity.getId()));
    }

    @Override
    public void processOnRenderList(PagingResult<TaxonomyItem> pagingResult, List<TaxonomyEntity> taxonomyEntities) {
        List<TaxonomyItem> items = pagingResult.getData();
        int n = items.size();
        if (! items.isEmpty()) {
            Collections.sort(items);
            items.get(0).setHighestPriority(true);
            items.get(n-1).setLowestPriority(true);
        }
    }

    private BreadcrumbView renderBreadcrumbView(String type, String subject, List<TaxonomyEntity> paths) {
        BreadcrumbView breadcrumbView = new BreadcrumbView();
        breadcrumbView.setRouteLinks(renderRouteLinkView(type, subject, paths));

        return breadcrumbView;
    }

    private List<RouteLinkView> renderRouteLinkView(String type, String subject, List<TaxonomyEntity> taxonomyEntities) {
        TaxonomyEntity root = taxonomyEntities.get(0);

        String routeName = (subject == null ? "taxonomy" : subject)
                + "-"
                + (type == null ? "" : (type + "-"))
                + "list";

        String taxonomyIdKey = subject == null ? "parentId" : "typeId";

        List<RouteLinkView> routeLinks = new ArrayList<>();
        for (TaxonomyEntity entity : taxonomyEntities) {
            RouteLinkView route = new RouteLinkView();
            route.setTitle(entity.getTitle());
            route.setName(routeName);
            route.addParam(taxonomyIdKey, entity.getId());
            routeLinks.add(route);
        }

        return routeLinks;
    }

    @Override
    public void processOnFormRender(TaxonomyEntity entity, TaxonomyForm taxonomyForm, TaxonomyFormRenderParam param) {
        if (taxonomyForm.getParentId() == null) {
            Integer parentId = param.getParentId();
            if (parentId != null && parentId > 0) {
                TaxonomyEntity parentEntity = taxonomyRepository.findById(parentId);
                taxonomyForm.setTypeId(parentEntity.getTypeId() == 0 ? parentEntity.getId() : parentEntity.getTypeId());
                taxonomyForm.setParentId(parentEntity.getId());
            } else {
                taxonomyForm.setTypeId(0);
                taxonomyForm.setParentId(0);
            }
        }
    }

    @Override
    public void processOnFormSaving(TaxonomyEntity entity, TaxonomyEntity originalEntity, TaxonomyForm formView) {
        if (CollectionUtils.isNotEmpty(formView.getRelationConfigs())) {
            entity.getConfig().add(formView.getRelationConfigs());
        }
    }

    @Override
    public void processOnSaving(TaxonomyEntity entity, TaxonomyEntity original) {
        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(entity.getParentId());

        Integer typeId = entity.getTypeId();
        Integer parentId = entity.getParentId();
        if (! parentId.equals(typeId)) {
            boolean typeMatched = paths.stream().anyMatch(p -> p.getId().equals(typeId));
            if (! typeMatched) {
                throw new ErrorCodeException(ErrorCode.of("taxonomy.type.parent.unmatched", typeId, parentId));
            }
        }

        if (null != original) {
            if (entity.getParentId() != null) {
                if (! original.getParentId().equals(entity.getParentId())) {
                    boolean match = paths.stream().anyMatch(p->p.getId().equals(entity.getId()));
                    if (match) {
                        throw new ErrorCodeException(ErrorCode.of("taxonomy.update.failed.parent.cycle"));
                    }
                }
            }
        } else {
            if (StringUtils.isBlank(entity.getSlug())) {
                entity.setSlug(SlugParser.parse(entity.getSubTitle()));
            } else {
                entity.setSlug(SlugParser.parse(entity.getSlug()));
            }

            TaxonomyEntity lowest = taxonomyRepository.findLowestPriority(entity.getParentId());
            int priority = 0;
            if (null != lowest) {
                priority = (lowest.getPriority() == null ? 0 : lowest.getPriority()) + 1;
            }
            entity.setPriority(priority);
        }
    }

    @Override
    public void processOnDelete(TaxonomyEntity entity) {
        int count = taxonomyRepository.countByParentId(entity.getId());
        if (count > 0) {
            throw new ErrorCodeException(ErrorCode.of("taxonomy.delete.failed.children", entity.getSubTitle(), entity.getTitle()));
        }
    }
}
