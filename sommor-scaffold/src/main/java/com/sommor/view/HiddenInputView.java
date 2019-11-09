package com.sommor.view;

import com.sommor.view.html.HtmlElement;

/**
 * @author yanguanwei@qq.com
 * @since 2019/11/7
 */
public class HiddenInputView extends FieldView {

    public static final String TYPE = "hidden-input";

    public HiddenInputView() {
        super(TYPE);
    }

    @Override
    public HtmlElement toHtml() {
        return toHtml("input", "value")
            .addProperty("type", "hidden");
    }
}
