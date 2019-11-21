package com.sommor.admin.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-10
 */
@Controller
public class AdminController {

    @RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "admin/test";
    }

}
