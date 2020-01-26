package com.sommor.scaffold.view;

import com.sommor.scaffold.view.field.FieldRenderContext;
import com.sommor.scaffold.view.field.Form;
import com.sommor.scaffold.view.field.FieldDefinition;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class FormView extends FieldsetView {

    private Form form;

    public FormView(Form form) {
        super("form", form.getTargetData());

        this.form = form;
        this.setName(form.getTargetClass().getSimpleName());
    }

    public void render() {
        this.renderFieldsetView(this);
    }

    public String getAction() {
        return this.form.getAction().action();
    }

    public String getActionTitle() {
        return this.form.getAction().actionTitle();
    }

    public Map<String, Object> getFields() {
        return this.toFields();
    }

    public Map<String, Object> getData() {
        return this.form.toData();
    }

    @Override
    protected FieldView newFieldView(FieldDefinition definition) {
        Class viewClass = definition.getViewClass();

        FieldView fieldView;
        if (null != viewClass) {
            try {
                fieldView = (FieldView) viewClass.newInstance();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            fieldView = super.newFieldView(definition);
        }

        return fieldView;
    }

    @Override
    protected void onFieldRender(FieldRenderContext ctx) {
        if (null != ctx.getDefinition()) {
            Annotation field = ctx.getDefinition().getFieldConfig();
            if (null != field) {
                processor.run(field, ext -> ext.processOnFormRender(field, ctx));
            }
        }

        super.onFieldRender(ctx);
    }
}
