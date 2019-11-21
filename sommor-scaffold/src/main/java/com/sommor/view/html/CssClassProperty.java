package com.sommor.view.html;

import java.util.*;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-10
 */
public class CssClassProperty extends HtmlProperty<List<String>> {

    public CssClassProperty() {
        super("class", new ArrayList<>());
    }

    public void addClass(String className) {
        getValue().add(className);
    }

    public void addClass(String... classNames) {
        getValue().addAll(Arrays.asList(classNames));
    }

    @Override
    protected String value2String() {
        return String.join(" ", getValue());
    }
}
