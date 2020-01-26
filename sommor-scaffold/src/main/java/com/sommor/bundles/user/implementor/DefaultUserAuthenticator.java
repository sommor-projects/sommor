package com.sommor.bundles.user.implementor;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.response.Response;
import com.sommor.extensibility.config.Implement;
import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.extension.Authenticator;
import com.sommor.bundles.user.model.Identity;
import com.sommor.bundles.user.repository.UserRepository;
import com.sommor.bundles.user.model.UserLoginParam;

import javax.annotation.Resource;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
@Implement
public class DefaultUserAuthenticator implements Authenticator<UserLoginParam> {

    @Resource
    private UserRepository userRepository;

    @Override
    public Response<Identity> authenticate(UserLoginParam userLoginParam) {
        UserEntity userEntity = userRepository.findByUserName(userLoginParam.getUserName());
        if (null == userEntity) {
            return Response.error(ErrorCode.of("login.userNotExist", userLoginParam.getUserName()));
        }

        String encryptedPassword = UserEntity.encryptPassword(userLoginParam.getPassword(), userEntity.getCreateTime());
        if (!encryptedPassword.equalsIgnoreCase(userEntity.getPassword())) {
            return Response.error(ErrorCode.of("login.passwordInvalid", userLoginParam.getUserName()));
        }

        return Response.success(new Identity(userEntity, null));
    }
}
