package com.sommor.bundles.taxonomy.controller;

import com.sommor.core.api.response.ApiResponse;
import com.sommor.bundles.taxonomy.model.*;
import com.sommor.bundles.taxonomy.service.TaxonomyService;
import com.sommor.core.component.form.FormView;
import com.sommor.core.utils.Converter;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@RestController
@RequestMapping(value = "/api/taxonomy")
//@Authority(roles = {"admin"})
public class TaxonomyController {

    @Resource
    private TaxonomyService taxonomyService;

    @ApiOperation(value = "更新分类的排序")
    @RequestMapping(value = "/update-priority", method = RequestMethod.POST)
    public ApiResponse updatePriority(@Validated @RequestBody TaxonomyPriorityForm taxonomyPriorityForm) {
        taxonomyService.updateTaxonomyPriority(Converter.parseLong(taxonomyPriorityForm.getId()), taxonomyPriorityForm.getDirection());
        return ApiResponse.success();
    }

    @ApiOperation(value = "渲染分类选择字段")
    @RequestMapping(value = "/form/subject", method = RequestMethod.GET)
    public ApiResponse<FormView> renderSubjectTaxonomySelect(@RequestParam String taxonomy) {
        FormView formView = taxonomyService.renderSubjectTaxonomyForm(taxonomy);
        return ApiResponse.success(formView);
    }

//    @Resource
//    private TaxonomyRepository taxonomyRepository;
//
//    @ApiOperation(value = "修复数据")
//    @RequestMapping(value = "/fix", method = RequestMethod.GET)
//    public ApiResponse<Integer> fixTaxonomy() {
//        int updateRows = 0;
//
//        List<TaxonomyEntity> taxonomyEntities = taxonomyRepository.findAll();
//        Map<Integer, TaxonomyEntity> taxonomyEntityMap = taxonomyEntities.stream().collect(Collectors.toMap(p->p.getId(), p->p));
//        Set<Integer> fixedSet = new HashSet<>();
//
//        for (TaxonomyEntity taxonomyEntity : taxonomyEntities) {
//            boolean update = fixTaxonomy(taxonomyEntityMap, taxonomyEntity, fixedSet);
//            if (update) {
//                updateRows++;
//            }
//        }
//
//        return ApiResponse.success(updateRows);
//    }

//    private boolean fixTaxonomy(Map<Integer, TaxonomyEntity> taxonomyEntityMap, TaxonomyEntity taxonomyEntity, Set<Integer> fixedSet) {
//        if (! fixedSet.contains(taxonomyEntity.getId())) {
//            fixedSet.add(taxonomyEntity.getId());
//
//            if (taxonomyEntity.getType() == null || taxonomyEntity.getParent() == null) {
//                if (taxonomyEntity.getTypeId() == 0) {
//                    taxonomyEntity.setType(TaxonomyEntity.ROOT);
//                } else {
//                    TaxonomyEntity typeEntity = taxonomyEntityMap.get(taxonomyEntity.getTypeId());
//                    if (null != typeEntity) {
//                        taxonomyEntity.setType(typeEntity.getName());
//                        String typePrefix = typeEntity.getName() + "-";
//                        if (taxonomyEntity.getName().startsWith(typePrefix)) {
//                            taxonomyEntity.setName(taxonomyEntity.getName().substring(typePrefix.length()));
//                        }
//                    }
//                }
//
//                if (taxonomyEntity.getParentId() == 0) {
//                    taxonomyEntity.setParent(TaxonomyEntity.ROOT);
//                } else {
//                    TaxonomyEntity parentEntity = taxonomyEntityMap.get(taxonomyEntity.getParentId());
//                    if (null != parentEntity) {
//                        fixTaxonomy(taxonomyEntityMap, parentEntity, fixedSet);
//                        taxonomyEntity.setParent(parentEntity.getName());
//                    }
//                }
//
//                taxonomyRepository.save(taxonomyEntity);
//                return true;
//            }
//        }
//        return false;
//    }
}
