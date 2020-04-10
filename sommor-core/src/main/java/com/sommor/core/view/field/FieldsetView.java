package com.sommor.core.view.field;

import com.sommor.core.view.View;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
public class FieldsetView extends View {

    @Getter
    @Setter
    private String fullName;

    @Getter
    @Setter
    private Map<String, Object> fields;

    public FieldsetView() {
        super("fieldset");
    }

    public FieldsetView(String tag) {
        super(tag);
    }
}
