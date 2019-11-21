package com.sommor.usercenter.service;

import com.sommor.usercenter.entity.UserEntity;
import com.sommor.usercenter.model.UserProfile;
import com.sommor.usercenter.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author young.alway@gmail.com
 * @since 2019-11-15
 */
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public UserProfile queryUserProfile(Integer userId) {
        UserEntity userEntity = userRepository.findById(userId);
        return UserProfile.from(userEntity);
    }

}
