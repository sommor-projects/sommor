package com.sommor.view.form;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.view.FieldView;
import com.sommor.view.FormView;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class AnnotatedFormField extends FormField {

    private FormFieldDefinition definition;

    private FormView formView;

    public AnnotatedFormField(FormFieldDefinition definition, FormView formView) {
        super(definition.getName(), definition.getViewClass());

        this.definition = definition;
        this.formView = formView;

        this.getConstraints().merge(definition.getConstraints());
    }

    @Override
    public Object getValue() {
        Object value = super.getValue();
        if (null == value) {
            try {
                value = this.definition.getMethod().invoke(this.formView);
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
        FormFieldDefinition annotation = this.definition;
        FormField formField = this;

        ExtensionExecutor.of(FormFieldResolver.class)
            .run(annotation.getConfig(),
                ext -> ext.resolveOnRender(annotation.getConfig(), fieldView, formView, formField)
            );

        return fieldView;
    }
}
