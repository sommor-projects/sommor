package com.sommor.view;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanguanwei@qq.com
 * @since 2020/2/13
 */
public class View {

    @Getter
    private String tag;

    @Getter
    @Setter
    private String name;

    public View(String tag) {
        this.tag = tag;
    }
}
