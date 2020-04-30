package com.sommor.core.component.form;

import com.sommor.core.view.model.ModelView;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
public class FormView extends ModelView {

    @Setter
    @Getter
    private String action;

    @Setter
    @Getter
    private String actionTitle;

    @Setter
    @Getter
    private Map<String, Object> data;

    public FormView() {
        super("form");
    }

    public static FormView of(FormView... formViews) {
        FormView merged = new FormView();

        FormView formView0 = formViews[0];
        merged.setAction(formView0.getAction());
        merged.setActionTitle(formView0.getActionTitle());

        Map<String, Object> data = new HashMap<>();
        merged.setData(data);

        Map<String, Object> fields = new HashMap<>();
        merged.setFields(fields);

        for (FormView formView : formViews) {
            data.put(formView.getName(), formView.getData());
            fields.put(formView.getName(), formView.getFields());
        }

        return merged;
    }
}
