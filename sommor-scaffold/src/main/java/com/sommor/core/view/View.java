package com.sommor.core.view;

import com.sommor.core.view.html.HtmlElement;
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
    private Boolean disabled;

    public View(String type) {
        this.type = type;
    }
}
