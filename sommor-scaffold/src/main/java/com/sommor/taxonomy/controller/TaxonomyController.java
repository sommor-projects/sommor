package com.sommor.taxonomy.controller;

import com.sommor.api.response.ApiResponse;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.taxonomy.entity.TaxonomyEntity;
import com.sommor.taxonomy.model.TaxonomyForm;
import com.sommor.taxonomy.model.TaxonomyTree;
import com.sommor.taxonomy.service.TaxonomyService;
import com.sommor.usercenter.config.Authority;
import com.sommor.view.html.HtmlElement;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@RestController
@RequestMapping(value = "/api")
@Authority(roles = {"admin"})
public class TaxonomyController {

    @Resource
    private TaxonomyService taxonomyService;

    @ApiOperation(value = "添加分类")
    @RequestMapping(value = "/taxonomy/add", method = RequestMethod.POST)
    public ApiResponse<TaxonomyEntity> add(@Validated @RequestBody TaxonomyForm taxonomyForm) {
        TaxonomyEntity entity = taxonomyService.save(taxonomyForm);
        return ApiResponse.success(entity);
    }

    @ApiOperation(value = "更新分类")
    @RequestMapping(value = "/taxonomy/update", method = RequestMethod.POST)
    public ApiResponse<TaxonomyEntity> update(@Validated @RequestBody TaxonomyForm taxonomyForm) {
        TaxonomyEntity entity = taxonomyService.save(taxonomyForm);
        return ApiResponse.success(entity);
    }

    @ApiOperation(value = "删除分类")
    @RequestMapping(value = "/taxonomy/{id}/delete", method = RequestMethod.GET)
    public ApiResponse<TaxonomyEntity> delete(@PathVariable("id") Integer id) {
        TaxonomyEntity entity = taxonomyService.delete(id);
        return ApiResponse.success(entity);
    }

    @ApiOperation(value = "获取分类表单")
    @RequestMapping(value = "/taxonomy/{rootId}/{parentId}/form", method = RequestMethod.GET)
    public ApiResponse<TaxonomyForm> form(
            @PathVariable("rootId") Integer rootId,
            @PathVariable("parentId") Integer parentId) {
        TaxonomyForm form = new TaxonomyForm();
        form.setRootId(rootId);
        form.setParentId(parentId);
        return ApiResponse.success(form);
    }

    @ApiOperation(value = "获取顶层分类列表")
    @RequestMapping(value = "/taxonomy/trees/root", method = RequestMethod.GET)
    public ApiResponse<List<TaxonomyTree>> getRootTaxonomyTrees() {
        List<TaxonomyTree> trees = taxonomyService.getRootTaxonomyTrees();
        return ApiResponse.success(trees);
    }

    @ApiOperation(value = "获取分类列表")
    @RequestMapping(value = "/taxonomy/trees", method = RequestMethod.GET)
    public ApiResponse<PagingResult<TaxonomyTree>> getTaxonomyTrees(Integer parentId) {
        List<TaxonomyTree> trees = taxonomyService.getTaxonomyTrees(parentId);
        return ApiResponse.success(PagingResult.of(trees));
    }
}
