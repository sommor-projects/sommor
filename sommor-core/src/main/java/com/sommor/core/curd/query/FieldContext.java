package com.sommor.core.curd.query;

import com.sommor.core.context.Context;
import com.sommor.core.model.Model;
import com.sommor.core.model.ModelField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public class FieldContext extends Context {

    @Getter
    @Setter
    private Model model;

    @Getter
    @Setter
    private ModelField modelField;

    public FieldContext() {
    }

    public FieldContext(Context ctx) {
        super(ctx);
    }

    public <V> V getFieldValue(String fieldName) {
        return model.getFieldValue(fieldName);
    }

    public <V> V getFieldValue() {
        return (V) this.modelField.getValue();
    }
}
