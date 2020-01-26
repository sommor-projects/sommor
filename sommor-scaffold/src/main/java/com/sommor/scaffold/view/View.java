package com.sommor.scaffold.view;

import com.sommor.scaffold.view.html.HtmlElement;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class View<V> {

    @Getter
    private String type;

    @Setter
    @Getter
    private String style;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Boolean disabled;

    private V value;

    private Map<String, Object> properties = new HashMap<>();

    public View(String type) {
        this.type = type;
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

    protected HtmlElement toHtml(String tag, String propertyKeyOfValue) {
        HtmlElement htmlElement = new HtmlElement(tag, propertyKeyOfValue)
                .addProperties(this.properties)
                .value(this.value());

        String name = getName();
        if (null != name) {
            htmlElement.addProperty("name", name);
        }

        return htmlElement;
    }
}
