package com.sommor.core.view.field;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/19
 */
public class FormSaveContext extends FormValidateContext {

    @Setter
    @Getter
    private DataSource original;

    public FormSaveContext(FormValidateContext ctx) {
        super(ctx);
    }
}
