package com.sommor.model.fill;

import com.sommor.core.context.Context;
import com.sommor.model.Model;
import com.sommor.model.ModelField;
import com.sommor.model.config.TargetConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/15
 */
public class FieldFillContext extends Context {

    @Getter
    @Setter
    private Model model;

    @Getter
    @Setter
    private Model sourceModel;

    @Getter
    @Setter
    private ModelField modelField;

    @Getter
    @Setter
    private TargetConfig fieldConfig;

    public FieldFillContext() {
    }

    public FieldFillContext(Context ctx) {
        super(ctx);
    }

    public <V> V getSourceModelFieldValue() {
        return this.sourceModel.getFieldValue(this.getModelField().getName());
    }
}
