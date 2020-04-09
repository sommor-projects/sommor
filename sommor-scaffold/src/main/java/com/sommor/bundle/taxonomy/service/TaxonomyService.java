package com.sommor.bundle.taxonomy.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.bundle.taxonomy.utils.SlugParser;
import com.sommor.component.form.FormView;
import com.sommor.component.form.FormViewConfig;
import com.sommor.component.form.action.Add;
import com.sommor.curd.CurdService;
import com.sommor.model.Model;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.sql.select.OrderType;
import com.sommor.bundle.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundle.taxonomy.model.*;
import com.sommor.bundle.taxonomy.repository.TaxonomyRepository;
import com.sommor.view.ViewEngine;
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
public class TaxonomyService extends CurdService<TaxonomyEntity> {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    public void updateTaxonomyPriority(Integer id, String direction) {
        TaxonomyEntity entity = taxonomyRepository.findById(id);
        if (null == entity) {
            throw new ErrorCodeException(ErrorCode.of("taxonomy.update.failed.absence", id));
        }

        Query query = new Query()
                .where("type", entity.getType())
                .where("parent", entity.getParent())
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

    public List<TaxonomyTree> getTaxonomyTreesByType(TaxonomyEntity typeEntity, String group, boolean includeSelf) {
        List<TaxonomyEntity> entities;

        if (StringUtils.isNotBlank(group)) {
            entities = taxonomyRepository.findAllByTypeAndGroup(typeEntity.getName(), group);
        } else {
            entities = taxonomyRepository.findAllByType(typeEntity.getName());
        }

        if (includeSelf) {
            entities.add(typeEntity);
        }

        Map<String, List<TaxonomyEntity>> map = mappedByParentId(entities);
        return parseTaxonomyTrees(map, includeSelf ? TaxonomyEntity.ROOT : typeEntity.getName());
    }

    private Map<String, List<TaxonomyEntity>> mappedByParentId(List<TaxonomyEntity> entities) {
        Map<String, List<TaxonomyEntity>> map = new HashMap<>();
        for (TaxonomyEntity entity : entities) {
            List<TaxonomyEntity> list = map.computeIfAbsent(entity.getParent(), p -> new ArrayList<>());
            list.add(entity);
        }
        return map;
    }

    private List<TaxonomyTree> parseTaxonomyTrees(Map<String, List<TaxonomyEntity>> map, String parent) {
        List<TaxonomyTree> trees = new ArrayList<>();

        List<TaxonomyEntity> entities = map.get(parent);
        if (null != entities) {
            for (TaxonomyEntity entity : entities) {
                TaxonomyTree taxonomyTree = new TaxonomyTree(entity);
                taxonomyTree.setChildren(parseTaxonomyTrees(map, entity.getName()));
                trees.add(taxonomyTree);
            }
            Collections.sort(trees);
        }

        return trees;
    }

    @Override
    protected void onSaving(TaxonomyEntity entity, TaxonomyEntity original) {
        super.onSaving(entity, original);

        List<TaxonomyEntity> paths = taxonomyRepository.findTaxonomyPaths(entity);

        if (paths.size() == 1) {
            entity.setType(TaxonomyEntity.ROOT);
        } else {
            entity.setType(paths.get(0).getName());
        }

        if (StringUtils.isNotBlank(entity.getTitle())) {
            entity.setTitle(entity.getTitle().trim());
        }

        if (StringUtils.isNotBlank(entity.getSubTitle())) {
            entity.setSubTitle(entity.getSubTitle().trim());
        }

        if (StringUtils.isBlank(entity.getName())) {
            String name = SlugParser.parse(entity.getSubTitle());
            entity.setName(name);
        }

        if (null != original) {
            // 编辑分类时，校验父分类不能指定为其下的某一子分类
            if (StringUtils.isNotBlank(entity.getParent())) {
                if (! original.getParent().equals(entity.getParent())) {
                    boolean match = paths.stream().anyMatch(p->p.getName().equals(entity.getName()));
                    if (match) {
                        throw new ErrorCodeException(ErrorCode.of("taxonomy.update.failed.parent.cycle"));
                    }
                }
            }
        } else {
            TaxonomyEntity lowest = taxonomyRepository.findLowestPriority(entity.getParent(), entity.getType());
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

        int count = taxonomyRepository.countByParent(entity.getName(), entity.getType());
        if (count > 0) {
            throw new ErrorCodeException(ErrorCode.of("taxonomy.delete.failed.children", entity.getSubTitle(), entity.getTitle()));
        }
    }

    public FormView renderSubjectTaxonomyForm(String type) {
        TaxonomyEntity typeEntity = taxonomyRepository.findByType(type);

        if (null == typeEntity) {
            throw new ErrorCodeException(ErrorCode.of("taxonomy.subject.select.type.invalid", type));
        }

        SubjectTaxonomyForm form = new SubjectTaxonomyForm(typeEntity);

        FormViewConfig config = new FormViewConfig();
        config.setModel(Model.of(form));
        config.setFormAction(Add.ACTION);

        return ViewEngine.render(config);
    }
}
