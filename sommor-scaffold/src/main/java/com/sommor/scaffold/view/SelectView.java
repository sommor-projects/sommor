package com.sommor.scaffold.view;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class SelectView extends FieldView {

    @Setter
    @Getter
    private List<Option> options;

    @Getter
    private boolean multiple;

    @Getter
    private boolean tree;

    public SelectView() {
        super("select");
    }

    protected SelectView(String type) {
        super(type);
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
}
