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

    public UserEntity register(UserRegisterParam userRegisterParam) {
        UserEntity userEntity = new UserEntity();

        initUserEntity(userEntity);
        initUserEntityFromParam(userEntity, userRegisterParam);

        Integer id = userRepository.insert(userEntity);
        userEntity.setId(id);

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

    private void initUserEntity(UserEntity userEntity) {
        Integer now = (int) (System.currentTimeMillis() / 1000);
        userEntity.setUpdateTime(now);
        userEntity.setCreateTime(now);
        userEntity.setStatus(UserStatus.NORMAL.getStatus());
    }
}
