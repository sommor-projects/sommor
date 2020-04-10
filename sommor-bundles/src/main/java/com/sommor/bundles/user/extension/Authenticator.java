package com.sommor.bundles.user.extension;

import com.sommor.core.api.response.Response;
import com.sommor.extensibility.config.Extension;
import com.sommor.bundles.user.model.Identity;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
@Extension(name = "用户登录认证器")
public interface Authenticator<LoginParam> {

    Response<Identity> authenticate(LoginParam loginParam);

}
