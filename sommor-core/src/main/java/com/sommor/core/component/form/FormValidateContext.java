package com.sommor.core.component.form;

import com.sommor.core.context.Context;
import com.sommor.core.model.Model;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/19
 */
public class FormValidateContext extends Context {

    @Getter
    @Setter
    private Model model;

    public FormValidateContext() {
    }

    public FormValidateContext(Context ctx) {
        super(ctx);
    }
}
