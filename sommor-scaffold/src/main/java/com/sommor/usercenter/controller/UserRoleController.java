package com.sommor.usercenter.controller;

import com.sommor.api.response.ApiResponse;
import com.sommor.api.response.Response;
import com.sommor.usercenter.config.Authority;
import com.sommor.usercenter.entity.UserRoleEntity;
import com.sommor.usercenter.model.LoginUser;
import com.sommor.usercenter.model.UserLoginParam;
import com.sommor.usercenter.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
@RestController
@Api("用户角色管理")
@RequestMapping("/api/user/role")
@Authority(roles = {"admin"})
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;

    @ApiOperation(value = "添加用户角色")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ApiResponse<UserRoleEntity> add(@RequestBody @Validated UserRoleEntity userRoleEntity) {
        userRoleEntity = userRoleService.add(userRoleEntity);
        return ApiResponse.success(userRoleEntity);
    }

    @ApiOperation(value = "删除用户角色")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ApiResponse<UserRoleEntity> delete(@PathVariable("id") Integer id) {
        UserRoleEntity userRoleEntity = userRoleService.delete(id);
        return ApiResponse.success(userRoleEntity);
    }
}
