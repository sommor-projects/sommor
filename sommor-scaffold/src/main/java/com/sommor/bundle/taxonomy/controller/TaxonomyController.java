package com.sommor.bundle.taxonomy.controller;

import com.sommor.api.response.ApiResponse;
import com.sommor.bundle.taxonomy.model.*;
import com.sommor.bundle.taxonomy.service.TaxonomyService;
import com.sommor.component.form.FormView;
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
