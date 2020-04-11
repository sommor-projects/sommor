package com.sommor.bundles.user.controller;

import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.model.UserQueryParam;
import com.sommor.bundles.user.model.UserTable;
import com.sommor.core.component.table.TableController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2020/4/11
 */
@RestController
@Api("用户列表")
@RequestMapping("/api/user")
public class UserTableController extends TableController<UserEntity, UserTable, UserQueryParam> {
}
