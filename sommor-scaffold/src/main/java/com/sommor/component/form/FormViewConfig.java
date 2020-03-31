package com.sommor.component.form;

import com.sommor.component.form.action.FormAction;
import com.sommor.view.model.ModelViewConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/14
 */
public class FormViewConfig extends ModelViewConfig<FormView> {

    @Getter
    @Setter
    private FormAction formAction;


    @Getter
    @Setter
    private String submitUrl;
}
