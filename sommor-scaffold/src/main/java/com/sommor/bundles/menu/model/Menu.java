package com.sommor.bundles.menu.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-10
 */
public class Menu extends MenuItem {

    private List<MenuItem> menuItems;

    private List<MenuItem> sortedMenuItems;

    public void addMenuItem(MenuItem menuItem) {
        if (null == menuItems) {
            menuItems = new ArrayList<>();
        }
        menuItems.add(menuItem);
        this.sortedMenuItems = null;
    }

    public List<MenuItem> getMenuItems() {
        if (null == this.sortedMenuItems) {
            List<MenuItem> sortedMenuItems = new ArrayList<>(this.menuItems);
            Collections.sort(sortedMenuItems);
            this.sortedMenuItems = sortedMenuItems;
        }

        return this.sortedMenuItems == null ? Collections.emptyList() : this.sortedMenuItems;
    }
}
