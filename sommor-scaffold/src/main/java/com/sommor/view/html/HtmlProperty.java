package com.sommor.view.html;

import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class HtmlProperty {

    @Getter
    private String key;

    private Object value;

    public HtmlProperty(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        String stringValue = "";
        if (null != value) {
            stringValue = value.toString();
        }
        return key + "=\"" + stringValue + "\"";
    }
}
