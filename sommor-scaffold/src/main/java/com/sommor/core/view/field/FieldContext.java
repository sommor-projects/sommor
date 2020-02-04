package com.sommor.core.view.field;

import com.sommor.scaffold.context.Context;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public class FieldContext extends Context {

    @Getter
    @Setter
    private FieldDefinition definition;

    @Getter
    @Setter
    private DataSource data;

    public FieldContext() {
    }

    public FieldContext(Context ctx) {
        super(ctx);
    }

    public <V> V getFieldValue(String fieldName) {
        return data.get(fieldName);
    }

    public <V> V getFieldValue() {
        return this.getFieldValue(this.definition.getName());
    }
}
