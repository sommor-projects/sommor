package com.sommor.menu.model;

import com.sommor.view.View;
import com.sommor.view.html.HtmlElement;
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

    @Override
    public HtmlElement toHtml() {
        HtmlElement ol = super.toHtml("ol")
                .cssClass("breadcrumb", "float-sm-right");
        activeMenu(this.menu, ol);
        return ol;
    }

    private void activeMenu(Menu menu, HtmlElement ol) {
        if (menu.isActive()) {

            ol.append(renderBreadcrumbItem(menu));

            for (MenuItem menuItem : menu.getMenuItems()) {
                if (menuItem instanceof Menu) {
                    activeMenu((Menu) menuItem, ol);
                } else {
                    if (menuItem.isActive()) {
                        ol.append(renderBreadcrumbItem(menuItem).cssClass("active"));
                        break;
                    }
                }
            }
        }
    }

    private HtmlElement renderBreadcrumbItem(MenuItem menuItem) {
        HtmlElement li = new HtmlElement("li")
                .cssClass("breadcrumb-item");

        if (StringUtils.isNotBlank(menuItem.getUrl())) {
            HtmlElement a = new HtmlElement("a")
                    .addProperty("href", menuItem.getUrl())
                    .value(menuItem.getTitle());

            li.append(a);
        } else {
            li.value(menuItem.getTitle());
        }

        return li;
    }
}
