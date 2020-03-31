package com.sommor.component.form;

import com.sommor.view.model.ModelView;
import lombok.Getter;
import lombok.Setter;

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
}
