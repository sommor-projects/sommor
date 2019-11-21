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
        private Object parentKey;
        private Object value;

        Option(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
        Option(Object key, Object parentKey, Object value) {
            this.key = key;
            this.parentKey = parentKey;
            this.value = value;
        }
    }

    @Setter
    @Getter
    private List<Option> options = new ArrayList<>();

    public SelectView() {
        super("select");
    }

    public void addOption(Object key, Object value) {
        this.options.add(new Option(key, value));
    }

    public void addOption(Object key, Object parentKey, Object value) {
        this.options.add(new Option(key, parentKey, value));
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
