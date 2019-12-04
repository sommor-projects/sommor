package com.sommor.view;

import com.sommor.view.html.HtmlElement;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class View<V> {

    private String type;

    private String name;

    private V value;

    private Map<String, Object> properties = new HashMap<>();

    public View(String type) {
        this.type = type;
    }

    public void name(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public void value(V value) {
        this.value = value;
    }

    public V value() {
        return this.value;
    }

    public <T> T getProperties(String key) {
        return (T) this.properties.get(key);
    }

    public void addProperty(String key, Object value) {
        this.properties.put(key, value);
    }

    public Object toJson() {
        return this;
    }

    public HtmlElement toHtml() {
        return toHtml(type, null);
    }

    protected HtmlElement toHtml(String tag) {
        return this.toHtml(tag, null);
    }

    protected HtmlElement toHtml(String tag, String propertyKeyOfValue) {
        HtmlElement htmlElement = new HtmlElement(tag, propertyKeyOfValue)
            .addProperties(this.properties)
            .value(this.value());

        String name = name();
        if (null != name) {
            htmlElement.addProperty("name", name);
        }

        return htmlElement;
    }
}
