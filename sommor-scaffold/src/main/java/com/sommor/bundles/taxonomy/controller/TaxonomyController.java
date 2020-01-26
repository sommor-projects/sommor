package com.sommor.bundles.taxonomy.controller;

import com.sommor.api.response.ApiResponse;
import com.sommor.bundles.taxonomy.view.TaxonomyTable;
import com.sommor.scaffold.controller.CurdController;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.view.TaxonomyForm;
import com.sommor.bundles.taxonomy.model.*;
import com.sommor.bundles.taxonomy.view.TaxonomyFormRenderParam;
import com.sommor.bundles.taxonomy.view.TaxonomyDetailParam;
import com.sommor.bundles.taxonomy.view.TaxonomyQueryParam;
import com.sommor.bundles.taxonomy.service.TaxonomyService;
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
public class TaxonomyController extends CurdController<
        TaxonomyEntity,
        TaxonomyForm,
        TaxonomyFormRenderParam,
        TaxonomyDetail,
        TaxonomyDetailParam,
        TaxonomyTable,
        TaxonomyQueryParam> {

    @Resource
    private TaxonomyService taxonomyService;

    @ApiOperation(value = "更新分类的排序")
    @RequestMapping(value = "/update-priority", method = RequestMethod.POST)
    public ApiResponse updatePriority(@Validated @RequestBody TaxonomyPriorityForm taxonomyPriorityForm) {
        taxonomyService.updateTaxonomyPriority(taxonomyPriorityForm.getId(), taxonomyPriorityForm.getDirection());
        return ApiResponse.success();
    }
}
