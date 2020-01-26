package com.sommor.scaffold.controller;

import com.sommor.api.response.ApiResponse;
import com.sommor.mybatis.entity.BaseEntity;
import com.sommor.mybatis.entity.definition.EntityManager;
import com.sommor.scaffold.param.EntityDeleteParam;
import com.sommor.scaffold.service.CurdManager;
import com.sommor.scaffold.service.CurdService;
import com.sommor.scaffold.utils.ClassAnnotatedTypeParser;
import com.sommor.scaffold.view.FormView;
import com.sommor.scaffold.view.DetailView;
import com.sommor.scaffold.view.TableView;
import com.sommor.scaffold.view.field.action.Add;
import com.sommor.scaffold.view.field.action.Edit;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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
        Form,
        FormRenderParam,
        Detail,
        DetailParam,
        Table,
        QueryParam>
        implements ApplicationListener<ContextRefreshedEvent> {

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
    public ApiResponse<TableView> renderTable(QueryParam queryParam) {
        TableView tableView = curdService().renderTable(queryParam);
        return ApiResponse.success(tableView);
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<DetailView> renderDetail(DetailParam param) {
        DetailView detailView = curdService().renderDetail(param);
        return ApiResponse.success(detailView);
    }

    @ApiOperation(value = "表单")
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<FormView> renderForm(FormRenderParam param) {
        FormView formView = (FormView) this.curdService().renderForm(param);
        return ApiResponse.success(formView);
    }

    @ApiOperation(value = "添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<Entity> add(@Validated({Add.class}) @RequestBody Form form) {
        Entity entity = (Entity) curdService().saveForm(form);
        return ApiResponse.success(entity);
    }

    @ApiOperation(value = "添加")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<Entity> edit(@Validated({Edit.class}) @RequestBody Form form) {
        Entity entity = (Entity) curdService().saveForm(form);
        return ApiResponse.success(entity);
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<Entity> delete(@RequestBody EntityDeleteParam param) {
        Integer id = param.getId();
        Entity entity = (Entity) curdService().delete(id);
        return ApiResponse.success(entity);
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete-batch", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<List<Entity>> deleteBatch(@RequestBody EntityDeleteParam param) {
        List<Entity> entities = curdService().deleteBatch(param.getIds());
        return ApiResponse.success(entities);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        EntityManager.getDefinition(this.entityClass);
    }
}


