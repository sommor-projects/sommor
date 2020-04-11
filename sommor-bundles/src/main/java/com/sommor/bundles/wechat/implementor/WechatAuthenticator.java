package com.sommor.bundles.wechat.implementor;

import com.sommor.core.api.error.ErrorCode;
import com.sommor.core.api.response.Response;
import com.sommor.extensibility.config.Implement;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.extension.Authenticator;
import com.sommor.bundles.user.model.Identity;
import com.sommor.bundles.user.model.UserRegisterParam;
import com.sommor.bundles.user.service.UserRegisterService;
import com.sommor.bundles.wechat.entity.WechatUserEntity;
import com.sommor.bundles.wechat.repository.WechatUserRepository;
import com.sommor.bundles.wechat.request.WechatLoginParam;
import com.sommor.bundles.wechat.sdk.WechatClient;
import com.sommor.bundles.wechat.sdk.WechatSession;

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
    private UserRegisterService userRegisterService;

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
        UserEntity userEntity = userRegisterService.register(userRegisterParam);
        WechatUserEntity wechatUserEntity = wechatUserRepository.findByOpenid(wechatSession.getOpenid());
        if (null == wechatUserEntity) {
            wechatUserEntity = new WechatUserEntity();
            wechatUserEntity.setOpenid(wechatSession.getOpenid());
            wechatUserEntity.setUnionid(wechatSession.getUnionid());
            wechatUserEntity.setUserId(userEntity.getId());

            wechatUserRepository.add(wechatUserEntity);
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
