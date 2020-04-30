package com.sommor.core.component.form;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/24
 */
public interface FormService<Entity, Form, FormParam> {

    FormView renderForm(FormParam param, Form form);

    Entity saveForm(Form form);

}
