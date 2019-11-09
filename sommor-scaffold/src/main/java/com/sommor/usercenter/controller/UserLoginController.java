package com.sommor.usercenter.controller;

import com.sommor.api.response.ApiResponse;
import com.sommor.api.response.Response;
import com.sommor.usercenter.model.Authentication;
import com.sommor.usercenter.request.UserLoginParam;
import com.sommor.usercenter.response.LoginUser;
import com.sommor.usercenter.service.AuthenticateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
@RestController
public class UserLoginController {

    @Autowired
    private AuthenticateService authenticateService;

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResponse<LoginUser> login(@RequestBody @Validated UserLoginParam userLoginParam) {
        Response<LoginUser> response = authenticateService.login(userLoginParam);
        return ApiResponse.of(response);
    }

    @ApiOperation(value = "用户登录验证")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ApiResponse<Authentication> login(String token) {
        Response<Authentication> response = authenticateService.authenticate(token);
        return ApiResponse.of(response);
    }
}
