package com.sommor.taxonomy.service;

import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.taxonomy.model.TaxonomyForm;
import com.sommor.taxonomy.model.TaxonomyTree;
import com.sommor.taxonomy.repository.TaxonomyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
@Service
public class TaxonomyService {

    @Resource
    private TaxonomyRepository taxonomyRepository;

    public TaxonomyEntity save(TaxonomyForm taxonomyForm) {
        TaxonomyEntity entity = taxonomyForm.toEntity();
        taxonomyRepository.save(entity);
        return entity;
    }

    public TaxonomyEntity delete(Integer id) {
        TaxonomyEntity entity = taxonomyRepository.findById(id);
        if (null != entity) {
            taxonomyRepository.deleteById(id);
        }
        return entity;
    }

    public List<TaxonomyTree> getTaxonomyTrees(Integer parentId) {
        List<TaxonomyEntity> entities = taxonomyRepository.findTaxonomiesByParentId(parentId);
        Map<Integer, List<TaxonomyEntity>> map = mappedByParentId(entities);
        return parseTaxonomyTrees(map, parentId);
    }

    public List<TaxonomyTree> getRootTaxonomyTrees() {
        List<TaxonomyEntity> entities = taxonomyRepository.findRootTaxonomies();
        Map<Integer, List<TaxonomyEntity>> map = mappedByParentId(entities);
        return parseTaxonomyTrees(map, 0);
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
                trees.add(taxonomyTree);
            }
        }

        Collections.sort(trees);

        return trees;
    }
}
