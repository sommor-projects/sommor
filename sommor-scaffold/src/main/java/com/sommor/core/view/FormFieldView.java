package com.sommor.core.view;

import com.sommor.core.view.field.FieldConstraints;
import com.sommor.core.view.field.FieldDefinition;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class FormFieldView extends FieldView {

    @Getter
    private FieldConstraints constraints = new FieldConstraints();

    @Getter
    @Setter
    private String fullName;

    public FormFieldView(String type) {
        super(type);
    }
}
