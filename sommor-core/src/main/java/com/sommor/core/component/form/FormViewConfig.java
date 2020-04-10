package com.sommor.core.component.form;

import com.sommor.core.component.form.action.FormAction;
import com.sommor.core.view.model.ModelViewConfig;
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
