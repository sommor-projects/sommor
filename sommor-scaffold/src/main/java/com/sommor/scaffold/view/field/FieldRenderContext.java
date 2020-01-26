package com.sommor.scaffold.view.field;

import com.sommor.scaffold.view.FieldView;
import com.sommor.scaffold.view.FieldsetView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/28
 */
public class FieldRenderContext extends FieldContext {

    @Getter
    @Setter
    private FieldsetView fieldsetView;

    @Setter
    private FieldView fieldView;

    public <FV extends FieldView> FV getFieldView() {
        return (FV) this.fieldView;
    }
}
