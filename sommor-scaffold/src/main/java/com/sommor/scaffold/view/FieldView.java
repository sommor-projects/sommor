package com.sommor.scaffold.view;

import com.sommor.scaffold.view.field.FieldConstraints;
import com.sommor.scaffold.view.field.FieldDefinition;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class FieldView extends View {

    @Setter
    protected FieldDefinition definition;

    @Getter
    private FieldConstraints constraints = new FieldConstraints();

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String fullName;

    public FieldView(String type) {
        super(type);
    }

    protected void onFieldRender() {
    }

    public FieldDefinition definition() {
        return this.definition;
    }
}
