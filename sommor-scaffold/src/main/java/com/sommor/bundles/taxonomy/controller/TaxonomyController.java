package com.sommor.bundles.taxonomy.controller;

import com.sommor.api.response.ApiResponse;
import com.sommor.bundles.taxonomy.view.*;
import com.sommor.core.curd.CurdController;
import com.sommor.bundles.taxonomy.entity.TaxonomyEntity;
import com.sommor.bundles.taxonomy.model.*;
import com.sommor.bundles.taxonomy.service.TaxonomyService;
import com.sommor.core.view.FormView;
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

    @ApiOperation(value = "渲染分类选择字段")
    @RequestMapping(value = "/form/subject", method = RequestMethod.GET)
    public ApiResponse<FormView> renderSubjectTaxonomySelect(@RequestParam String taxonomy) {
        FormView formView = taxonomyService.renderSubjectTaxonomyForm(taxonomy);
        return ApiResponse.success(formView);
    }
}
