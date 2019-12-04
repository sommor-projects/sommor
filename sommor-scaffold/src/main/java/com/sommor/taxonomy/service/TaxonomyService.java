package com.sommor.taxonomy.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.error.ErrorCodeException;
import com.sommor.mybatis.query.Query;
import com.sommor.mybatis.sql.select.OrderType;
import com.sommor.scaffold.service.CurdService;
import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.taxonomy.form.TaxonomyForm;
import com.sommor.taxonomy.model.*;
import com.sommor.taxonomy.param.TaxonomyFormRenderParam;
import com.sommor.taxonomy.param.TaxonomyInfoParam;
import com.sommor.taxonomy.param.TaxonomyQueryParam;
import com.sommor.taxonomy.repository.TaxonomyRepository;
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
        TaxonomyInfoParam,
        TaxonomyItem,
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
        List<TaxonomyEntity> entities = taxonomyRepository.findByTypeId(typeId);

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
}
