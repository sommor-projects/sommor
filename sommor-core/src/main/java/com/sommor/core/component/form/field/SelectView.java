package com.sommor.core.component.form.field;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
public class SelectView extends FormFieldView {

    @Getter
    @Setter
    private List<Option> options;

    @Getter
    @Setter
    private Boolean multiple;

    @Getter
    @Setter
    private Boolean tree;

    public SelectView() {
        super("select");
    }

    public SelectView(String tag) {
        super(tag);
    }

    public void addOption(Option option) {
        if (null == this.options) {
            this.options = new ArrayList<>();
        }
        this.options.add(option);
    }

    public void addOption(String label, Object value) {
        this.addOption(new Option(label, value));
    }

    public void multiple() {
        this.multiple = true;
    }

    public void tree() {
        this.tree = true;
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value.toString());
    }
}
