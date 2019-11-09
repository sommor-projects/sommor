package com.sommor.wechat.implementor;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.response.Response;
import com.sommor.extensibility.config.Implement;
import com.sommor.usercenter.entity.UserEntity;
import com.sommor.usercenter.extension.Authenticator;
import com.sommor.usercenter.model.Identity;
import com.sommor.usercenter.request.UserRegisterParam;
import com.sommor.usercenter.response.LoginUser;
import com.sommor.usercenter.service.RegisterService;
import com.sommor.wechat.entity.WechatUserEntity;
import com.sommor.wechat.repository.WechatUserRepository;
import com.sommor.wechat.request.WechatLoginParam;
import com.sommor.wechat.sdk.WechatClient;
import com.sommor.wechat.sdk.WechatSession;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
@Implement
public class WechatAuthenticator implements Authenticator<WechatLoginParam> {

    @Resource
    private WechatClient wechatClient;

    @Resource
    private WechatUserRepository wechatUserRepository;

    @Resource
    private RegisterService registerService;

    @Override
    public Response<Identity> authenticate(WechatLoginParam wechatLoginParam) {
        String code = wechatLoginParam.getCode();

        WechatSession wechatSession = wechatClient.jscode2session(code);

        if (! wechatSession.isSuccess()) {
            return Response.error(ErrorCode.of("wechat.login.failed",
                wechatSession.getErrcode(),
                wechatSession.getErrmsg()
            ));
        }

        UserRegisterParam userRegisterParam = parseUserRegisterParam(wechatSession);
        Response<UserEntity> response = registerService.register(userRegisterParam);
        if (! response.isSuccess()) {
            return Response.error(response);
        }

        UserEntity userEntity = response.getResult();

        WechatUserEntity wechatUserEntity = wechatUserRepository.findByOpenid(wechatSession.getOpenid());
        if (null == wechatUserEntity) {
            wechatUserEntity = new WechatUserEntity();
            wechatUserEntity.setOpenid(wechatSession.getOpenid());
            wechatUserEntity.setUnionid(wechatSession.getUnionid());
            wechatUserEntity.setUserId(userEntity.getId());

            wechatUserRepository.insert(wechatUserEntity);
        }


        Identity identity = new Identity(userEntity, wechatSession.getSession_key());
        return Response.success(identity);
    }

    private UserRegisterParam parseUserRegisterParam(WechatSession wechatSession) {
        UserRegisterParam userRegisterParam = new UserRegisterParam();
        userRegisterParam.setUsername("wexin_" + wechatSession.getUnionid());


        return userRegisterParam;
    }
}
