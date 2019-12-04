package com.sommor.view;

import com.sommor.view.form.EntityForm;
import com.sommor.view.html.HtmlElement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/26
 */
public class FieldsetView extends FieldView {

    private Map<String, FieldView> fields = new HashMap<>();

    public FieldsetView() {
        super("fieldset");
    }

    public void addField(FieldView fieldView) {
        this.fields.put(fieldView.name(), fieldView);
    }

    public FieldView getField(String fieldName) {
        return this.fields.get(fieldName);
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

    @Override
    public void renderForm(EntityForm form) {
        super.renderForm(form);

        for (FieldView fieldView : this.fields.values()) {
            fieldView.renderForm(form);
        }
    }

    public Map<String, Object> toValues() {
        Map<String, Object> fieldValues = new HashMap<>();
        for (FieldView fieldView : this.fields.values()) {
            if (fieldView instanceof FieldsetView) {
                fieldValues.put(fieldView.getName(), ((FieldsetView) fieldView).toValues());
            } else {
                Object v = fieldView.value();
                String fieldName = fieldView.getName();
                if (null != v) {
                    fieldValues.put(fieldName, v);
                }
            }
        }

        return fieldValues;
    }

    @Override
    public HtmlElement toHtml() {
        return super.toHtml();
    }
}
