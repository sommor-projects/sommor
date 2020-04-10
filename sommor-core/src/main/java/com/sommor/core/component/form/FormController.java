package com.sommor.core.component.form;

import com.sommor.core.api.response.ApiResponse;
import com.sommor.core.component.form.action.Add;
import com.sommor.core.component.form.action.Edit;
import com.sommor.core.utils.ClassAnnotatedTypeParser;
import com.sommor.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/14
 */
public class FormController<Entity extends BaseEntity, Form, FormParam> {

    private FormService formService;

    public FormController() {
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        this.formService = new FormService(classes[0], classes[1]);
    }

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @ApiOperation(value = "表单")
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<FormView> renderForm(@Validated FormParam param) {
        FormView formView = this.formService.renderEntityForm(param);
        return ApiResponse.success(formView);
    }

    @ApiOperation(value = "添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<Entity> addForm(@Validated({Add.class}) @RequestBody Form form) {
        Entity entity = (Entity) this.formService.saveEntityForm(form);
        return ApiResponse.success(entity);
    }

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<Entity> editForm(@Validated({Edit.class}) @RequestBody Form form) {
        Entity entity = (Entity) this.formService.saveEntityForm(form);
        return ApiResponse.success(entity);
    }
}
