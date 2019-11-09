package com.sommor.view;

import com.sommor.view.html.HtmlElement;
import lombok.Getter;

import java.util.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class SelectView extends FieldView {

    @Getter
    private class Option {
        private String key;
        private Object value;

        Option(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    private List<Option> options = new ArrayList<>();

    public SelectView() {
        super("select");
    }

    public void addOption(String key, Object value) {
        this.options.add(new Option(key, value));
    }

    public void multiple() {
        this.addProperty("multiple", "multiple");
    }

    @SuppressWarnings("unchecked")
    @Override
    public HtmlElement toHtml() {
        List<HtmlElement> options = new ArrayList<>();

        Set selectedSet = new HashSet();
        Object selected = getValue();
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
