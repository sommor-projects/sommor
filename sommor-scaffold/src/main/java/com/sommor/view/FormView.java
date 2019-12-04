package com.sommor.view;

import com.sommor.view.form.*;
import com.sommor.view.html.HtmlElement;

import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class FormView extends View {

    private FieldsetView fieldsetView;

    private EntityForm form;

    public FormView(EntityForm form) {
        super("form");

        this.form = form;
        this.name(form.getClass().getSimpleName());

        this.fieldsetView = this.initFormFieldset(form);
    }

    public void addField(FieldView fieldView) {
        this.fieldsetView.addField(fieldView);
    }

    public FieldView getField(String fieldName) {
        return this.fieldsetView.getField(fieldName);
    }

    public FieldsetView getFieldset(String fieldsetName) {
        return (FieldsetView) this.fieldsetView.getField(fieldsetName);
    }

    public void render() {
        this.fieldsetView.renderForm(this.form);
    }

    public Map<String, Object> getFields() {
        return this.fieldsetView.toFields();
    }

    public Map<String, Object> getValues() {
        return this.form.toValues();
    }

    private FieldsetView initFormFieldset(EntityForm form) {
        FormDefinition formDefinition = FormManager.getFormDefinition(form.getClass());

        FieldsetView fieldsetView = new FieldsetView();
        initFieldsetView(formDefinition.getFieldsetDefinition(), fieldsetView, form);

        return fieldsetView;
    }

    private void initFieldsetView(FormFieldsetDefinition definition, FieldsetView view, EntityForm form) {
        if (null != definition.getFields()) {
            for (FormFieldDefinition fd : definition.getFields()) {
                FieldView fieldView;
                try {
                    fieldView = (FieldView) fd.getViewClass().newInstance();
                    fieldView.setName(fd.getName());
                    fieldView.setFullName(view.getName() == null ? fd.getName() : (view.getName() + "." + fd.getName()));
                    fieldView.setDefinition(fd);
                    fieldView.getConstraints().merge(fd.getConstraints());
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }

                if (fd instanceof FormFieldsetDefinition) {
                    FormFieldsetDefinition subDefinition = (FormFieldsetDefinition) fd;
                    initFieldsetView((FormFieldsetDefinition) fd, (FieldsetView) fieldView, form);
                }

                view.addField(fieldView);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public HtmlElement toHtml() {
        return super.toHtml()
            .value(this.fieldsetView.toHtml());
    }
}
