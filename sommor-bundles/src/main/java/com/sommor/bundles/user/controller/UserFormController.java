package com.sommor.bundles.user.controller;

import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.model.UserForm;
import com.sommor.core.component.form.FormController;
import com.sommor.core.scaffold.param.EntityFormParam;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
@RestController
@Api("用户管理")
@RequestMapping("/api/user")
public class UserFormController extends FormController<UserEntity, UserForm, EntityFormParam> {
}
