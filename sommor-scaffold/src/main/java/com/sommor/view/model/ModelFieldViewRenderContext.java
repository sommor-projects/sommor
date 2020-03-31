package com.sommor.view.model;

import com.sommor.core.context.Context;
import com.sommor.model.Model;
import com.sommor.model.config.TargetConfig;
import com.sommor.view.View;
import com.sommor.view.ViewConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/28
 */
public class ModelFieldViewRenderContext extends Context {

    @Setter
    private TargetConfig fieldConfig;

    @Getter
    @Setter
    private Model sourceMode;

    @Getter
    @Setter
    private Model model;

    public ModelFieldViewRenderContext(Context ctx) {
        super(ctx);
    }


    public <T extends TargetConfig> T getFieldConfig() {
        return (T) fieldConfig;
    }
}
