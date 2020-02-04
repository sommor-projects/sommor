package com.sommor.core.view;

import com.sommor.extensibility.ExtensionExecutor;
import com.sommor.core.view.field.DataSource;
import com.sommor.core.view.field.FieldProcessor;
import com.sommor.core.view.field.FieldRenderContext;
import com.sommor.core.view.field.OnFieldRender;
import com.sommor.core.view.field.FieldDefinition;
import com.sommor.core.view.field.FieldManager;
import com.sommor.core.view.field.FieldsetDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/26
 */
public class FieldsetView extends FieldView {
    private DataSource targetData;

    private Map<String, FieldView> fields = new HashMap<>();

    private FieldsetView parent;

    protected static ExtensionExecutor<FieldProcessor> processor = ExtensionExecutor.of(FieldProcessor.class);

    public FieldsetView() {
        super("fieldset");
    }

    public FieldsetView(String type, DataSource targetData) {
        super(type);
        this.targetData = targetData;

        this.init(targetData.getTarget().getClass());
    }

    private void init(Class targetClass) {
        FieldsetDefinition fieldset = FieldManager.getFieldset(targetClass);
        initFieldsetView(fieldset, this);
    }

    private String getFieldNamePrefix() {
        if (null != parent) {
            String parentFieldNamePrefix = parent.getFieldNamePrefix();
            return parentFieldNamePrefix == null ? this.getName() : (parentFieldNamePrefix + "." + this.getName());
        }

        return null;
    }

    private void initFieldsetView(FieldsetDefinition definition, FieldsetView view) {
        if (null != definition.getFields()) {

            for (FieldDefinition fd : definition.getFields()) {
                FieldView fieldView;
                try {
                    fieldView = newFieldView(fd);
                    fieldView.setDefinition(fd);

                    fieldView.setName(fd.getName());
                    fieldView.setTitle(fd.getTitle());
                    fieldView.setStyle(fd.getStyle());
                    fieldView.setDefinition(fd);

                    if (fieldView instanceof FormFieldView) {
                        FormFieldView formFieldView = (FormFieldView) fieldView;

                        String fieldNamePrefix = view.getFieldNamePrefix();
                        String fullName = fieldNamePrefix == null ? fd.getName() : (fieldNamePrefix + "." + fd.getName());
                        formFieldView.setFullName(fullName);

                        formFieldView.getConstraints().merge(fd.getConstraints());
                    }
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }

                if (fd instanceof FieldsetDefinition) {
                    FieldsetDefinition subDefinition = (FieldsetDefinition) fd;
                    FieldsetView subView = (FieldsetView) fieldView;
                    subView.parent = this;
                    initFieldsetView(subDefinition, subView);
                }

                view.addField(fieldView);
            }
        }
    }

    public void addField(FieldView fieldView) {
        this.fields.put(fieldView.getName(), fieldView);
    }

    public FieldView getField(String fieldName) {
        return this.fields.get(fieldName);
    }

    public FieldsetView getFieldset(String fieldsetName) {
        return (FieldsetView) this.getField(fieldsetName);
    }

    public Map<String, Object> toFields() {
        Map<String, Object> fieldViewMap = new HashMap<>();

        for (FieldView fieldView : this.fields.values()) {
            if (fieldView instanceof FieldsetView) {
                fieldViewMap.put(fieldView.getName(), ((FieldsetView) fieldView).toFields());
            } else {
                fieldViewMap.put(fieldView.getName(), fieldView);
            }
        }

        return fieldViewMap;
    }

    protected void renderFieldsetView(FieldsetView fieldsetView) {
        this.renderFieldView(fieldsetView, fieldsetView);

        for (FieldView fieldView : fieldsetView.fields.values()) {
            if (fieldView instanceof FieldsetView) {
                this.renderFieldsetView((FieldsetView) fieldView);
            } else {
                this.renderFieldView(fieldView, fieldsetView);
            }
        }
    }

    private void renderFieldView(FieldView fieldView, FieldsetView fieldsetView) {
        FieldRenderContext ctx = new FieldRenderContext();
        ctx.setFieldsetView(fieldsetView);
        ctx.setData(targetData);

        FieldDefinition fd = fieldView.definition;
        ctx.setDefinition(fd);
        ctx.setFieldView(fieldView);

        this.onFieldRender(ctx);

        Object target = targetData.getTarget();
        if (target instanceof OnFieldRender) {
            ((OnFieldRender) target).onFieldRender(fieldView);
        }

        if (fd != null && null != fd.getRender()) {
            try {
                fd.getRender().invoke(targetData.getTarget(), ctx);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        fieldView.onFieldRender();
    }

    protected void onFieldRender(FieldRenderContext ctx) {
    }

    protected FieldView newFieldView(FieldDefinition definition) {
        return new TextView();
    }
}
