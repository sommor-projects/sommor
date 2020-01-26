package com.sommor.scaffold.view;

import lombok.Getter;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/15
 */
@Getter
public class Option {
    private String key;
    private String label;
    private String value;

    public Option(String label, Object value) {
        this.label = label;
        this.value = value.toString();
        this.key = this.value;
    }
}
