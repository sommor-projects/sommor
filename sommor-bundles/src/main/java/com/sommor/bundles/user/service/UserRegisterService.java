package com.sommor.bundles.user.service;

import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.model.UserStatus;
import com.sommor.bundles.user.repository.UserRepository;
import com.sommor.bundles.user.model.UserRegisterParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wuyu
 * @since 2019/2/3
 */
@Service
public class UserRegisterService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserService userService;

    public UserEntity register(UserRegisterParam userRegisterParam) {
        UserEntity userEntity = new UserEntity();
        initUserEntityFromParam(userEntity, userRegisterParam);
        userService.save(userEntity);

        return userEntity;
    }

    private void initUserEntityFromParam(UserEntity entity, UserRegisterParam userRegisterParam) {
        entity.setUserName(userRegisterParam.getUsername());
        entity.setNickName(userRegisterParam.getNickname());

        String encryptedPassword = UserEntity.encryptPassword(
                userRegisterParam.getPassword(),
                entity.getCreateTime()
        );
        entity.setPassword(encryptedPassword);

        entity.setEmail(userRegisterParam.getEmail());
        entity.setMobilePhone(userRegisterParam.getMobilePhone());
        entity.setGender(userRegisterParam.getGender());
        entity.setAvatar(userRegisterParam.getAvatar());
    }
}
