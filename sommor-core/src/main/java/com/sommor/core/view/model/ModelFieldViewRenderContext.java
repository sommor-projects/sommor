package com.sommor.core.view.model;

import com.sommor.core.context.Context;
import com.sommor.core.model.Model;
import com.sommor.core.model.config.TargetConfig;
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
