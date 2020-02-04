package com.sommor.bundles.taxonomy.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundles.taxonomy.utils.SlugParser;
import com.sommor.bundles.taxonomy.view.*;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.sql.select.OrderType;
import com.sommor.core.curd.CurdService;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.model.*;
import com.sommor.bundles.taxonomy.repository.TaxonomyRepository;
import com.sommor.core.view.FormView;
import com.sommor.core.view.field.action.Add;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
@Service
public class TaxonomyService extends CurdService<
        TaxonomyEntity,
        TaxonomyForm,
        TaxonomyFormRenderParam,
        TaxonomyDetail,
        TaxonomyDetailParam,
        TaxonomyTable,
        TaxonomyQueryParam> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    public void updateTaxonomyPriority(Integer id, String direction) {
        TaxonomyEntity entity = taxonomyRepository.findById(id);
        if (null == entity) {
            throw new ErrorCodeException(ErrorCode.of("taxonomy.update.failed.absence", id));
        }

        Query query = new Query()
                .where("parentId", entity.getParentId())
                .orderly("priority", OrderType.ASC);

        List<TaxonomyEntity> entities = taxonomyRepository.find(query);
        if (null != entities && entities.size() > 1) {
            LinkedList<TaxonomyEntity> list = new LinkedList<>(entities);
            int index = -1;
            int size = list.size();
            for (int i=0; i<size; i++) {
                TaxonomyEntity e = list.get(i);
                if (e.getId().equals(entity.getId())) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                TaxonomyEntity removed = list.remove(index);
                if ("forward".equals(direction) && index > 0) {
                    list.add(index-1, removed);
                } else if ("backward".equals(direction) && index < (size-1)) {
                    list.add(index+1, removed);
                } else {
                    throw new ErrorCodeException(ErrorCode.of("taxonomy.update.priority.failed.direction", direction, index, size));
                }

                int i=0;
                for (TaxonomyEntity e : list) {
                    taxonomyRepository.updatePriorityById(e.getId(), i++);
                }
            }
        }
    }

    public List<TaxonomyTree> getTaxonomyTreesByType(Integer typeId, boolean includeSelf) {
        return this.getTaxonomyTreesByType(typeId, null, includeSelf);
    }

    public List<TaxonomyTree> getTaxonomyTreesByType(Integer typeId, String group, boolean includeSelf) {
        List<TaxonomyEntity> entities;

        if (StringUtils.isNotBlank(group)) {
            entities = taxonomyRepository.findByTypeIdAndGroup(typeId, group);
        } else {
            entities = taxonomyRepository.findByTypeId(typeId);
        }

        if (includeSelf) {
            TaxonomyEntity root = taxonomyRepository.findById(typeId);
            entities.add(root);
        }

        Map<Integer, List<TaxonomyEntity>> map = mappedByParentId(entities);
        return parseTaxonomyTrees(map, includeSelf ? 0 : typeId);
    }

    private Map<Integer, List<TaxonomyEntity>> mappedByParentId(List<TaxonomyEntity> entities) {
        Map<Integer, List<TaxonomyEntity>> map = new HashMap<>();
        for (TaxonomyEntity entity : entities) {
            List<TaxonomyEntity> list = map.computeIfAbsent(entity.getParentId(), p -> new ArrayList<>());
            list.add(entity);
        }
        return map;
    }

    private List<TaxonomyTree> parseTaxonomyTrees(Map<Integer, List<TaxonomyEntity>> map, Integer parentId) {
        List<TaxonomyTree> trees = new ArrayList<>();

        List<TaxonomyEntity> entities = map.get(parentId);
        if (null != entities) {
            for (TaxonomyEntity entity : entities) {
                TaxonomyTree taxonomyTree = new TaxonomyTree(entity);
                taxonomyTree.setChildren(parseTaxonomyTrees(map, entity.getId()));
                trees.add(taxonomyTree);
            }
            Collections.sort(trees);
        }

        return trees;
    }

    @Override
    protected void onSaving(TaxonomyEntity entity, TaxonomyEntity original) {
        super.onSaving(entity, original);

        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(entity.getParentId());

        if (CollectionUtils.isEmpty(paths)) {
            entity.setTypeId(0);
        } else {
            entity.setTypeId(paths.get(0).getId());
        }

        if (StringUtils.isBlank(entity.getName())) {
            String name = SlugParser.parse(entity.getSubTitle());
            if (entity.getParentId() > 0 && paths.size() > 0) {
                name = paths.get(0).getName() + "-" + name;
            }
            entity.setName(name);
        }

        if (null != original) {
            // 编辑分类时，校验父分类ID不能指定为其下的某一子分类ID
            if (entity.getParentId() != null) {
                if (! original.getParentId().equals(entity.getParentId())) {
                    boolean match = paths.stream().anyMatch(p->p.getId().equals(entity.getId()));
                    if (match) {
                        throw new ErrorCodeException(ErrorCode.of("taxonomy.update.failed.parent.cycle"));
                    }
                }

                // 编辑分类时，校验父分类ID不能为其他类型下的分类ID
                Integer parentId = entity.getParentId();
                Integer typeId = original.getTypeId();
                if (! parentId.equals(typeId)) {
                    boolean typeMatched = paths.stream().anyMatch(p -> p.getId().equals(typeId));
                    if (! typeMatched) {
                        throw new ErrorCodeException(ErrorCode.of("taxonomy.type.parent.unmatched", typeId, parentId));
                    }
                }
            }
        } else {
            TaxonomyEntity lowest = taxonomyRepository.findLowestPriority(entity.getParentId());
            int priority = 0;
            if (null != lowest) {
                priority = (lowest.getPriority() == null ? 0 : lowest.getPriority()) + 1;
            }
            entity.setPriority(priority);
        }
    }

    @Override
    protected void onDelete(TaxonomyEntity entity) {
        super.onDelete(entity);

        int count = taxonomyRepository.countByParentId(entity.getId());
        if (count > 0) {
            throw new ErrorCodeException(ErrorCode.of("taxonomy.delete.failed.children", entity.getSubTitle(), entity.getTitle()));
        }
    }

    public FormView renderSubjectTaxonomyForm(String type) {
        TaxonomyEntity typeEntity = taxonomyRepository.findByName(type);

        if (null == typeEntity) {
            throw new ErrorCodeException(ErrorCode.of("taxonomy.subject.select.type.invalid", type));
        }

        SubjectTaxonomyForm form = new SubjectTaxonomyForm(typeEntity);

        return renderForm(form, null, Add.ACTION);
    }
}
