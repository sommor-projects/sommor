package com.sommor.menu.controller;

import com.sommor.menu.model.MenuForm;
import com.sommor.menu.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-10
 */
@Controller
public class MenuController {

    @Resource
    private MenuService menuService;

    @RequestMapping(value = "/menu/test", method = RequestMethod.GET)
    public String test(ModelMap modelMap) {

        modelMap.put("menu", menuService);

        return "admin/test";
    }

    @RequestMapping(value = "/menu/add", method = RequestMethod.GET)
    public String add() {
        MenuForm menuForm = new MenuForm();

        return "menu/form";
    }


}
