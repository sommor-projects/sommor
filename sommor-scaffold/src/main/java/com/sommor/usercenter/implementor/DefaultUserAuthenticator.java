package com.sommor.usercenter.implementor;

import com.sommor.api.error.ErrorCode;
import com.sommor.api.response.Response;
import com.sommor.extensibility.config.Implement;
import com.sommor.usercenter.entity.UserEntity;
import com.sommor.usercenter.extension.Authenticator;
import com.sommor.usercenter.model.Identity;
import com.sommor.usercenter.repository.UserRepository;
import com.sommor.usercenter.request.UserLoginParam;
import com.sommor.usercenter.utils.Encryption;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yanguanwei@qq.com
 * @since 2019/10/28
 */
@Implement
public class DefaultUserAuthenticator implements Authenticator<UserLoginParam> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Response<Identity> authenticate(UserLoginParam userLoginParam) {
        UserEntity userEntity = userRepository.findByTelephone(userLoginParam.getUsername());
        if (null == userEntity) {
            return Response.error(ErrorCode.of("login.userNotExist", userLoginParam.getUsername()));
        }

        if (!Encryption.md5(userLoginParam.getPassword()).equalsIgnoreCase(userEntity.getPassword())) {
            return Response.error(ErrorCode.of("login.-passwordInvalid", userLoginParam.getUsername()));
        }

        return Response.success(new Identity(userEntity, null));
    }
}