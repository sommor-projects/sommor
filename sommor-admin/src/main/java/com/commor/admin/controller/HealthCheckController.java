package com.commor.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/19
 */
@RestController
@RequestMapping
public class HealthCheckController {

    @RequestMapping(value = "/health-check", method = RequestMethod.GET)
    public String healthCheck() {
        return "OK";
    }

}
