package com.sommor.bundle.user.controller;

import com.sommor.api.response.ApiResponse;
import com.sommor.bundle.user.entity.UserEntity;
import com.sommor.bundle.user.model.UserRegisterParam;
import com.sommor.bundle.user.service.UserRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-14
 */
@RestController
@Api("用户注册")
@RequestMapping("/api/user")
public class UserRegisterController {

    @Resource
    private UserRegisterService userRegisterService;

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResponse<UserEntity> login(@RequestBody @Validated UserRegisterParam userRegisterParam) {
        UserEntity userEntity = userRegisterService.register(userRegisterParam);
        return ApiResponse.success(userEntity);
    }

}
