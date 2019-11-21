package com.sommor.view.html;

import com.sommor.view.View;

import java.util.*;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class HtmlElement {

    private String tag;

    private Object value;

    private List<HtmlElement> prependElements;

    private List<HtmlElement> appendElements;

    private String propertyKeyOfValue;

    private boolean hasClosingTag = true;

    private Map<String, HtmlProperty> properties = new HashMap<>();

    public HtmlElement(String tag) {
        this(tag, null, true);
    }

    public HtmlElement(String tag, String propertyKeyOfValue) {
        this(tag, propertyKeyOfValue, true);
    }

    public HtmlElement(String tag, boolean hasClosingTag) {
        this(tag, null, hasClosingTag);
    }

    public HtmlElement(String tag, String propertyKeyOfValue, boolean hasClosingTag) {
        this.tag = tag;
        this.propertyKeyOfValue = propertyKeyOfValue;
        this.hasClosingTag = hasClosingTag;
    }

    public HtmlElement value(Object value) {
        this.value = value;
        return this;
    }

    public HtmlElement append(HtmlElement element) {
        if (null == this.appendElements) {
            this.appendElements = new ArrayList<>();
        }
        this.appendElements.add(element);
        return this;
    }

    public HtmlElement prepend(HtmlElement element) {
        if (null == this.prependElements) {
            this.prependElements = new ArrayList<>();
        }
        this.prependElements.add(element);
        return this;
    }

    public HtmlElement addProperties(Map<String, Object> properties) {
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            this.addProperty(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public HtmlElement cssClass(String className) {
        cssClass().addClass(className);
        return this;
    }

    public HtmlElement cssClass(String... classNames) {
        cssClass().addClass(classNames);
        return this;
    }

    private CssClassProperty cssClass() {
        CssClassProperty property = (CssClassProperty) this.properties.get("class");
        if (null == property) {
            property = new CssClassProperty();
            this.addProperty(property);
        }
        return property;
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
        if (null != propertyKeyOfValue) {
            builder.append(" ").append(propertyKeyOfValue).append("=\"").append(stringValue).append("\"");
        }

        if (hasClosingTag) {
            builder.append(">");

            if (null != prependElements) {
                for (int i=prependElements.size()-1; i>=0; i--) {
                    builder.append(prependElements.get(i));
                }
            }

            if (null == propertyKeyOfValue) {
                builder.append(stringValue);
            }

            if (null != appendElements) {
                for (HtmlElement appendElement : appendElements) {
                    builder.append(appendElement);
                }
            }

            builder.append("</").append(tag).append(">");
        } else {
            builder.append(" />");
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
