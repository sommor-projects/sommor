package com.sommor.view.html;

import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class HtmlProperty<ValueType> {

    @Getter
    private String key;

    @Getter
    private ValueType value;

    public HtmlProperty(String key, ValueType value) {
        this.key = key;
        this.value = value;
    }

    protected String value2String() {
        String stringValue = "";
        if (null != value) {
            stringValue = value.toString();
        }

        return stringValue;
    }

    @Override
    public String toString() {
        return key + "=\"" + value2String() + "\"";
    }
}
