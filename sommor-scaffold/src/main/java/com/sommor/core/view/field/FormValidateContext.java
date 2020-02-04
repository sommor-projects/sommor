package com.sommor.core.view.field;

import com.sommor.scaffold.context.Context;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/19
 */
public class FormValidateContext extends Context {

    @Getter
    @Setter
    private DataSource data;

    public FormValidateContext() {
    }

    public FormValidateContext(Context ctx) {
        super(ctx);
    }
}
