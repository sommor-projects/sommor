package com.sommor.wechat.service;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.response.Response;
import com.sommor.usercenter.response.LoginUser;
import com.sommor.usercenter.service.AuthenticateService;
import com.sommor.wechat.sdk.WechatClient;
import com.sommor.wechat.sdk.WechatSession;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
public class WechatService {

    @Resource
    private WechatClient wechatClient;
}
