package com.sommor.bundles.wechat.controller;

import com.sommor.core.api.response.ApiResponse;
import com.sommor.core.api.response.Response;
import com.sommor.bundles.user.model.LoginUser;
import com.sommor.bundles.user.service.AuthenticateService;
import com.sommor.bundles.wechat.request.WechatLoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
@RestController
@Api("微信登录")
@RequestMapping("/wechat")
public class WechatLoginController {

    @Resource
    private AuthenticateService authenticateService;

    @ApiOperation(value = "微信用户登录")
    @RequestMapping(value = "/wechat/login", method = RequestMethod.GET)
    public ApiResponse<LoginUser> login(WechatLoginParam wechatLoginParam) {
        Response<LoginUser> response = authenticateService.login(wechatLoginParam);
        return ApiResponse.of(response);
    }
}
