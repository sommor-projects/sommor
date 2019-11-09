package com.sommor.view.html;

import com.sommor.view.View;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class HtmlElement {

    private String tag;

    private Object value;

    private String propertyKeyOfValue;

    private Map<String, HtmlProperty> properties = new HashMap<>();

    public HtmlElement(String tag) {
        this(tag, null);
    }

    public HtmlElement(String tag, String propertyKeyOfValue) {
        this.tag = tag;
        this.propertyKeyOfValue = propertyKeyOfValue;
    }

    public HtmlElement value(Object value) {
        this.value = value;
        return this;
    }

    public HtmlElement addProperties(Map<String, Object> properties) {
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            this.addProperty(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public HtmlElement addProperty(String key, Object value) {
        return this.addProperty(new HtmlProperty(key, value));
    }

    public HtmlElement addProperty(HtmlProperty htmlProperty) {
        this.properties.put(htmlProperty.getKey(), htmlProperty);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("<").append(tag);

        if (! properties.isEmpty()) {
            for (HtmlProperty property : properties.values()) {
                builder.append(" ").append(property.toString());
            }
        }

        String stringValue = parseValue(value);

        if (null == propertyKeyOfValue) {
            builder.append(">").append(stringValue).append("</").append(tag).append(">");
        } else {
            builder.append(" ").append(propertyKeyOfValue).append("=\"").append(stringValue).append("\" />");
        }

        return builder.toString();
    }

    private String parseValue(Object value) {
        if (null == value) {
            return "";
        }

        if (value instanceof View) {
            return ((View) value).toHtml().toString();
        } else if (value instanceof Collection) {
            StringBuilder sb = new StringBuilder();
            for (Object v : (Collection) value) {
                sb.append(parseValue(v));
            }
            return sb.toString();
        }

        return value.toString();
    }
}
