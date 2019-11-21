package com.sommor.menu.service;

import com.sommor.menu.model.BreadcrumbView;
import com.sommor.menu.model.Menu;
import com.sommor.menu.model.MenuItem;
import com.sommor.menu.model.MenuView;
import org.springframework.stereotype.Service;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-10
 */
@Service
public class MenuService {

    public MenuView renderMenu() {
        return new MenuView(mockMenu());
    }

    private Menu mockMenu() {
        Menu menu = new Menu();
        menu.setActive(true);
        menu.setTitle("Home");
        menu.setUrl("#");

        Menu dashboardMenu = new Menu();
        dashboardMenu.setIcon("fa-tachometer-alt");
        dashboardMenu.setTitle("Dashboard");
        dashboardMenu.setActive(true);

        MenuItem dashboardMenu1 = new MenuItem();
        dashboardMenu1.setTitle("Dashboard V1");
        dashboardMenu1.setIcon("fa-circle");
        dashboardMenu1.setActive(true);
        dashboardMenu1.setUrl("#");

        dashboardMenu.addMenuItem(dashboardMenu1);

        menu.addMenuItem(dashboardMenu);

        return menu;
    }

    public BreadcrumbView renderBreadcrumb() {
        return new BreadcrumbView(mockMenu());
    }
}
