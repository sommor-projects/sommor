package com.sommor.view.form;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.view.FieldView;
import com.sommor.view.FormView;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class AnnotatedFormField extends FormField {

    private FormFieldDefinition annotation;

    private FormView formView;

    public AnnotatedFormField(FormFieldDefinition annotation, FormView formView) {
        super(annotation.getName(), annotation.getViewClass());

        this.annotation = annotation;
        this.formView = formView;
    }

    @Override
    public Object getValue() {
        Object value = super.getValue();
        if (null == value) {
            try {
                value = this.annotation.getMethod().invoke(this.formView);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        return value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public FieldView newFieldView() {
        FieldView fieldView = super.newFieldView();
        FormFieldDefinition annotation = this.annotation;
        FormField formField = this;

        ExtensionExecutor.of(FormFieldResolver.class)
            .run(annotation.getConfig(),
                ext -> ext.resolveOnRender(annotation.getConfig(), fieldView, formView, formField)
            );

        return fieldView;
    }
}
