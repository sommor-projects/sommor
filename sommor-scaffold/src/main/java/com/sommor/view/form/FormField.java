package com.sommor.view.form;

import com.sommor.view.FieldConstraints;
import com.sommor.view.FieldView;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
@Getter
public class FormField {

    private String name;

    private Class viewClass;

    @Setter
    private Object value;

    private FieldConstraints constraints = new FieldConstraints();

    public FormField(String name, Class viewClass) {
        this.name = name;
        this.viewClass = viewClass;
    }

    public FieldView newFieldView() {
        try {
            FieldView fieldView = (FieldView) getViewClass().newInstance();

            fieldView.setValue(value);
            fieldView.setName(getName());
            fieldView.getConstraints().merge(getConstraints());

            return fieldView;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "FormField["+name+"]";
    }
}
