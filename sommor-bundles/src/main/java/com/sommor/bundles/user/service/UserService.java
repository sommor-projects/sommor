package com.sommor.bundles.user.service;

import com.sommor.bundles.user.entity.UserEntity;
import com.sommor.bundles.user.model.UserProfile;
import com.sommor.bundles.user.repository.UserRepository;
import com.sommor.core.curd.CurdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-15
 */
@Service
public class UserService extends CurdService<UserEntity> {

    @Resource
    private UserRepository userRepository;

    public UserProfile queryUserProfile(Integer userId) {
        UserEntity userEntity = userRepository.findById(userId);

        UserProfile userProfile = new UserProfile();
        userProfile.fill(userEntity);

        return userProfile;
    }

    @Override
    protected void onSaving(UserEntity entity, UserEntity originalEntity) {
        super.onSaving(entity, originalEntity);
        if (null == originalEntity) {
            entity.setStatus(UserEntity.STATUS_NORMAL);
        }
    }
}
