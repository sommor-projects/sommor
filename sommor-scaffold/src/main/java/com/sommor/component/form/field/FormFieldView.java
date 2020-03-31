package com.sommor.component.form.field;

import com.sommor.component.form.Constraint;
import com.sommor.view.View;
import com.sommor.view.field.FieldView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
public class FormFieldView extends FieldView {

    @Getter
    @Setter
    private Object value;

    @Getter
    @Setter
    private String fullName;

    @Getter
    @Setter
    private String style;

    @Getter
    @Setter
    private Constraint constraint;

    public FormFieldView(String tag) {
        super(tag);
    }
}
