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
public class View {

    @Getter
    private String type;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Object value;

    @Getter
    private Map<String, Object> properties = new HashMap<>();

    public View(String type) {
        this.type = type;
    }

    public void addProperty(String key, Object value) {
        this.properties.put(key, value);
    }

    public HtmlElement toHtml() {
        return toHtml(type, null);
    }

    protected HtmlElement toHtml(String tag, String propertyKeyOfValue) {
        HtmlElement htmlElement = new HtmlElement(tag, propertyKeyOfValue)
            .addProperties(this.getProperties())
            .value(this.getValue());

        String name = getName();
        if (null != name) {
            htmlElement.addProperty("name", name);
        }

        return htmlElement;
    }

    public Object toJson() {
        return this;
    }
}
