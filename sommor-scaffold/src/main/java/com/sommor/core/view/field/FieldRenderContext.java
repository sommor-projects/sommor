package com.sommor.core.view.field;

import com.sommor.core.view.FieldView;
import com.sommor.core.view.FormFieldView;
import com.sommor.core.view.FieldsetView;
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

    public <FV extends FormFieldView> FV getFieldView() {
        return (FV) this.fieldView;
    }
}
