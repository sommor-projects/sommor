package com.sommor.taxonomy.controller;

import com.sommor.api.response.ApiResponse;
import com.sommor.scaffold.controller.CurdController;
import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.taxonomy.form.TaxonomyForm;
import com.sommor.taxonomy.model.*;
import com.sommor.taxonomy.param.TaxonomyFormRenderParam;
import com.sommor.taxonomy.param.TaxonomyInfoParam;
import com.sommor.taxonomy.param.TaxonomyQueryParam;
import com.sommor.taxonomy.service.TaxonomyService;
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
        TaxonomyInfoParam,
        TaxonomyItem,
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
