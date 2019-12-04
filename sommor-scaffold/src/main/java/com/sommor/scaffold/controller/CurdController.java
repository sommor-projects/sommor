package com.sommor.scaffold.controller;

import com.sommor.api.response.ApiResponse;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.query.EntityQueryParam;
import com.sommor.mybatis.query.PagingResult;
import com.sommor.mybatis.query.Query;
import com.sommor.scaffold.param.EntityFormRenderParam;
import com.sommor.scaffold.param.EntityInfoParam;
import com.sommor.scaffold.service.CurdManager;
import com.sommor.scaffold.service.CurdService;
import com.sommor.scaffold.utils.ClassAnnotatedTypeParser;
import com.sommor.view.FormView;
import com.sommor.view.PageView;
import com.sommor.view.form.EntityForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/28
 */
public class CurdController<
        Entity extends BaseEntity,
        Form extends EntityForm<Entity>,
        FormRenderParam extends EntityFormRenderParam,
        EntityInfo,
        InfoParam extends EntityInfoParam,
        EntityListItem,
        QueryParam extends EntityQueryParam> {

    private Class entityClass;

    @Resource
    private CurdManager curdManager;

    private CurdService curdService;

    public CurdController() {
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        if (null == classes || classes.length == 0) {
            throw new RuntimeException("unknown entity class for controller: " + this.getClass().getName());
        }
        this.entityClass = classes[0];
    }

    @SuppressWarnings("unchecked")
    protected CurdService curdService() {
        if (null == this.curdService) {
            CurdService curdService = curdManager.getCurdService(this.entityClass);
            if (null == curdService) {
                throw new RuntimeException("unknown curd service for controller: " + this.getClass().getName());
            }

            this.curdService = curdService;
        }
        return this.curdService;
    }

    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<PagingResult<EntityListItem>> renderList(QueryParam queryParam) {
        PagingResult<EntityListItem> pagingResult = curdService().renderList(queryParam);
        return ApiResponse.success(pagingResult);
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<PageView<EntityInfo>> renderInfo(InfoParam param) {
        PageView<EntityInfo> pageView = curdService().renderInfo(param);
        return ApiResponse.success(pageView);
    }

    @ApiOperation(value = "表单")
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<FormView> renderForm(FormRenderParam param) {
        FormView formView = (FormView) this.curdService().renderForm(param);
        return ApiResponse.success(formView);
    }

    @ApiOperation(value = "保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<Entity> save(@Validated @RequestBody Form form) {
        Entity entity = (Entity) curdService().saveForm(form);
        return ApiResponse.success(entity);
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<Entity> delete(@RequestParam("id") Integer id) {
        Entity entity = (Entity) curdService().delete(id);
        return ApiResponse.success(entity);
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete-batch", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<List<Entity>> deleteBatch(@RequestParam("ids") List<Integer> ids) {
        List<Entity> entities = curdService().deleteBatch(ids);
        return ApiResponse.success(entities);
    }
}
