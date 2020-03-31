package com.sommor.bundle.user.extension;

import com.sommor.api.response.Response;
import com.sommor.extensibility.config.Extension;
import com.sommor.bundle.user.model.Identity;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
@Extension(name = "用户登录认证器")
public interface Authenticator<LoginParam> {

    Response<Identity> authenticate(LoginParam loginParam);

}
