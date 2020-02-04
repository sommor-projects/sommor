package com.sommor.bundles.user.controller;

import com.sommor.bundles.user.view.UserQueryParam;
import com.sommor.bundles.user.service.UserService;
import com.sommor.bundles.user.view.UserDetail;
import com.sommor.bundles.user.view.UserTable;
import com.sommor.core.curd.CurdController;
import com.sommor.scaffold.param.EntityFormRenderParam;
import com.sommor.scaffold.param.EntityDetailParam;
import com.sommor.bundles.user.config.Authority;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.view.UserForm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/12/15
 */
@RestController
@RequestMapping(value = "/api/user")
@Authority(roles = {"admin"})
public class UserController extends CurdController<
        UserEntity,
        UserForm,
        EntityFormRenderParam,
        UserDetail,
        EntityDetailParam,
        UserTable,
        UserQueryParam> {

    @Resource
    private UserService userService;
}
