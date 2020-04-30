package com.sommor.core.component.form;

import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.error.ErrorCodeException;
import com.sommor.core.api.response.ApiResponse;
import com.sommor.core.component.form.action.Add;
import com.sommor.core.component.form.action.Edit;
import com.sommor.core.utils.ClassAnnotatedTypeParser;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/14
 */
public class FormController<Entity , Form, FormParam> {

    private FormService formService;

    private Class formClass;

    public FormController() {
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        this.formService = new EntityFormService(classes[0], classes[1]);
        this.formClass = classes[1];
    }

    public FormController(FormService formService) {
        this.formService = formService;
        Class[] classes = ClassAnnotatedTypeParser.parse(this.getClass());
        this.formClass = classes[1];
    }

    @ApiOperation(value = "表单")
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public ApiResponse<FormView> renderForm(@Validated FormParam param) {
        FormView formView = this.formService.renderForm(param, this.newForm());
        return ApiResponse.success(formView);
    }

    private Form newForm() {
        try {
            return (Form) this.formClass.newInstance();
        } catch (Throwable e) {
            throw new ErrorCodeException(ErrorCode.of("form.render.new.exception", this.formClass.getSimpleName()));
        }
    }

    @ApiOperation(value = "添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<Entity> addForm(@Validated({Add.class}) @RequestBody Form form) {
        Entity entity = (Entity) this.formService.saveForm(form);
        return ApiResponse.success(entity);
    }

    @ApiOperation(value = "编辑")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public ApiResponse<Entity> editForm(@Validated({Edit.class}) @RequestBody Form form) {
        Entity entity = (Entity) this.formService.saveForm(form);
        return ApiResponse.success(entity);
    }
}
