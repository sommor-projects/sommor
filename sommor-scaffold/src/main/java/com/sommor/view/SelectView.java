package com.sommor.view;

import com.sommor.view.html.HtmlElement;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class SelectView extends FieldView {

    @Getter
    private class Option {
        private Object key;
        private String label;
        private Object value;

        Option(String label, Object value) {
            this.label = label;
            this.value = value;
            this.key = value;
        }
    }

    @Setter
    @Getter
    private List<Option> options;

    @Setter
    @Getter
    private List<OptionTree> optionTrees;

    public SelectView() {
        super("select");
    }

    public void addOptionTree(OptionTree optionTree) {
        if (null == this.optionTrees) {
            this.optionTrees = new ArrayList<>();
        }
        this.optionTrees.add(optionTree);
    }

    public void addOption(String label, Object value) {
        if (null == this.options) {
            this.options = new ArrayList<>();
        }
        this.options.add(new Option(label, value));
    }

    public void multiple() {
        this.addProperty("multiple", "multiple");
    }

    @SuppressWarnings("unchecked")
    @Override
    public HtmlElement toHtml() {
        List<HtmlElement> options = new ArrayList<>();

        Set selectedSet = new HashSet();
        Object selected = value();
        if (null != selected) {
            if (selected instanceof Collection) {
                selectedSet.addAll((Collection) selected);
            } else {
                selectedSet.add(selected);
            }
        }

        for (Option option : this.options) {
            HtmlElement optionElement = new HtmlElement("option");
            optionElement.value(option.getValue());
            if (selectedSet.contains(option.getValue())) {
                optionElement.addProperty("selected", "true");
            }
            options.add(optionElement);
        }

        return super.toHtml("select", null)
            .value(options);

    }
}
