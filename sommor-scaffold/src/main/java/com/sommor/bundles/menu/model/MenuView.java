package com.sommor.bundles.menu.model;

import com.sommor.scaffold.view.View;
import com.sommor.scaffold.view.html.HtmlElement;
import org.apache.commons.lang3.StringUtils;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-10
 */
public class MenuView extends View {

    private Menu menu;

    public MenuView(Menu menu) {
        super("menus");
        this.menu = menu;
    }

    private HtmlElement createMenuHtml(Menu menu) {
        HtmlElement ul = super.toHtml("ul", null)
                .cssClass("nav");

        for (MenuItem menuItem : menu.getMenuItems()) {
            HtmlElement li = new HtmlElement("li");
            li.cssClass("nav-item");

            HtmlElement a = new HtmlElement("a");
            a.cssClass("nav-link");

            if (menuItem.isActive()) {
                a.cssClass("active");
            }

            HtmlElement icon = new HtmlElement("i")
                    .cssClass("nav-icon", "fas");
            if (StringUtils.isNoneBlank(menuItem.getIcon())) {
                icon.cssClass(menuItem.getIcon());
            }

            HtmlElement p = new HtmlElement("p");
            p.value(menuItem.getTitle());

            a.append(icon);
            a.append(p);

            li.append(a);

            if (menuItem instanceof Menu) {
                li.cssClass("has-treeview");
                if (menuItem.isActive()) {
                    li.cssClass("menu-open");
                }

                HtmlElement subUl = this.createMenuHtml((Menu) menuItem);
                subUl.cssClass("nav-treeview");
                li.append(subUl);

                a.addProperty("href", "#");

                p.append(new HtmlElement("i").cssClass("right", "fas", "fa-angle-left"));
            } else {
                a.addProperty("href", menuItem.getUrl());
            }

            ul.append(li);
        }

        return ul;
    }
}
