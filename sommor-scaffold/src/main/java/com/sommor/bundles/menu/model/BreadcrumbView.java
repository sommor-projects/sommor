package com.sommor.bundles.menu.model;

import com.sommor.scaffold.view.View;
import com.sommor.scaffold.view.html.HtmlElement;
import org.apache.commons.lang3.StringUtils;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-10
 */
public class BreadcrumbView extends View {
    private Menu menu;

    public BreadcrumbView(Menu menu) {
        super("breadcrumb");

        this.menu = menu;
    }
}
