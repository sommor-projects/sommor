package com.sommor.core.view;

import com.sommor.core.view.field.FieldDefinition;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/4
 */
public class FieldView extends View {

    @Setter
    @Getter
    private String style;

    @Getter
    @Setter
    private String title;

    @Setter
    protected FieldDefinition definition;

    public FieldView(String type) {
        super(type);
    }

    public FieldDefinition definition() {
        return this.definition;
    }

    protected void onFieldRender() {
    }
}
